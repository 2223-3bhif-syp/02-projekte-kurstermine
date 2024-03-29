package at.htl.courseschedule.controller;

import at.htl.courseschedule.entity.Instructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository implements Persistent<Instructor> {
    private final DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Instructor instructor) {
        throwExceptionOnInvalidInstructor(instructor);

        if (instructor.getId() == null) {
            insert(instructor);
        }
        else {
            update(instructor);
        }
    }

    @Override
    public void update(Instructor instructor) {
        throwExceptionOnInvalidInstructor(instructor);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "UPDATE CS_INSTRUCTOR SET I_FIRST_NAME=?, I_LAST_NAME=?, I_PHONE_NR=?, I_EMAIL=? WHERE I_ID=?";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, instructor.getFirstName());
            statement.setString(2, instructor.getLastName());
            statement.setString(3, instructor.getPhoneNr());
            statement.setString(4, instructor.getEmail());
            statement.setLong(5, instructor.getId());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_INSTRUCTOR failed, no rows affected");
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Instructor instructor) {
        throwExceptionOnInvalidInstructor(instructor);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "INSERT INTO CS_INSTRUCTOR (I_FIRST_NAME, I_LAST_NAME, I_PHONE_NR, I_EMAIL) VALUES (?,?,?,?)";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, instructor.getFirstName());
            statement.setString(2, instructor.getLastName());
            statement.setString(3, instructor.getPhoneNr());
            statement.setString(4, instructor.getEmail());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_INSTRUCTOR failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    instructor.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into CS_INSTRUCTOR failed, no ID obtained");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Instructor instructor) {
        throwExceptionOnInvalidInstructor(instructor);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "DELETE FROM CS_INSTRUCTOR WHERE I_ID=?";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, instructor.getId());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_INSTRUCTOR failed, no rows affected");
            }

            instructor.setId(null);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Instructor> findAll() {
        List<Instructor> instructors = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "SELECT I_ID, I_FIRST_NAME, I_LAST_NAME, I_PHONE_NR, I_EMAIL FROM CS_INSTRUCTOR";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()) {
                Instructor instructor = new Instructor(result.getString("I_FIRST_NAME"),
                        result.getString("I_LAST_NAME"), result.getString("I_PHONE_NR"),
                        result.getString("I_EMAIL"));

                instructor.setId((long)result.getInt("I_ID"));

                instructors.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructors;
    }

    @Override
    public Instructor findById(long id) {
        Instructor instructor = null;

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "SELECT I_ID, I_FIRST_NAME, I_LAST_NAME, I_PHONE_NR, I_EMAIL FROM CS_INSTRUCTOR WHERE I_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                instructor = new Instructor(result.getString("I_FIRST_NAME"), result.getString("I_LAST_NAME"),
                        result.getString("I_PHONE_NR"), result.getString("I_EMAIL"));
                instructor.setId((long)result.getInt("I_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructor;
    }

    private void throwExceptionOnInvalidInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor can not be null!");
        }
    }
}
