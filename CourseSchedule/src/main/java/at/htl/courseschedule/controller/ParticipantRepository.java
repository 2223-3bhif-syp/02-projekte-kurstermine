package at.htl.courseschedule.controller;

import at.htl.courseschedule.entity.Participant;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepository implements Persistent<Participant> {
    private final DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Participant participant) {
        if (participant == null) {
            return;
        }

        if (participant.getId() == null) {
            insert(participant);
        }
        else {
            update(participant);
        }
    }

    @Override
    public void update(Participant participant) {
        if (participant == null) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE CS_PARTICIPANT SET P_FIRST_NAME=?, P_LAST_NAME=?, P_YEAR_OF_BIRTH=?, P_PHONE_NR=?, P_EMAIL=? WHERE P_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, participant.getFirstName());
            statement.setString(2, participant.getLastName());
            statement.setInt(3, participant.getYearOfBirth());
            statement.setString(4, participant.getPhoneNr());
            statement.setString(5, participant.getEmail());
            statement.setLong(6, participant.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_PARTICIPANT failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Participant participant) {
        if (participant == null) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO CS_PARTICIPANT (P_FIRST_NAME, P_LAST_NAME, P_YEAR_OF_BIRTH, P_PHONE_NR, P_EMAIL) VALUES (?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, participant.getFirstName());
            statement.setString(2, participant.getLastName());
            statement.setInt(3, participant.getYearOfBirth());
            statement.setString(4, participant.getPhoneNr());
            statement.setString(5, participant.getEmail());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_PARTICIPANT failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    participant.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into CS_PARTICIPANT failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Participant participant) {
        if (participant == null) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM CS_PARTICIPANT WHERE P_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, participant.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_PARTICIPANT failed, no rows affected");
            }
            participant.setId(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Participant> findAll() {
        List<Participant> participants = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM CS_PARTICIPANT";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()) {
                Participant participant = new Participant(result.getString("P_FIRST_NAME"),
                        result.getString("P_LAST_NAME"), result.getInt("P_YEAR_OF_BIRTH"),
                        result.getString("P_PHONE_NR"), result.getString("P_EMAIL"));

                participant.setId((long)result.getInt("P_ID"));

                participants.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participants;
    }

    @Override
    public Participant findById(long id) {
        Participant participant = null;

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT P_ID, P_FIRST_NAME, P_LAST_NAME, P_YEAR_OF_BIRTH, P_PHONE_NR, P_EMAIL " +
                    "FROM CS_PARTICIPANT WHERE P_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                participant = new Participant(result.getString("P_FIRST_NAME"), result.getString("P_LAST_NAME"),
                        result.getInt("P_YEAR_OF_BIRTH"), result.getString("P_PHONE_NR"), result.getString("P_EMAIL"));
                participant.setId((long)result.getInt("P_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participant;
    }
}
