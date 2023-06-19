package at.htl.courseschedule.controller;

import at.htl.courseschedule.entity.Registration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepository implements Persistent<Registration> {
    private final DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Registration registration) {
        throwExceptionOnInvalidAppointment(registration);

        if (registration.getId() == null) {
            insert(registration);
        } else {
            update(registration);
        }
    }

    @Override
    public void update(Registration registration) {
        throwExceptionOnInvalidAppointment(registration);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "UPDATE CS_REGISTRATION SET R_A_ID=?, R_P_ID=? WHERE R_ID=?";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, registration.getAppointment().getId());
            statement.setLong(2, registration.getParticipant().getId());
            statement.setLong(3, registration.getId());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_REGISTRATION failed, no rows affected");
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Registration registration) {
        throwExceptionOnInvalidAppointment(registration);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "INSERT INTO CS_REGISTRATION (R_P_ID, R_A_ID) VALUES (?,?)";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, registration.getAppointment().getId());
            statement.setLong(2, registration.getParticipant().getId());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_REGISTRATION failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    registration.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into CS_REGISTRATION failed, no ID obtained");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Registration registration) {
        throwExceptionOnInvalidAppointment(registration);

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "DELETE FROM CS_REGISTRATION WHERE R_ID=?";
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, registration.getId());

            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Update of CS_REGISTRATION failed, no rows affected");
            }

            registration.setId(null);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Registration> findAll() {
        List<Registration> registrations = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "SELECT R_ID, R_P_ID, R_A_ID FROM CS_REGISTRATION";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Registration registration = new Registration();
                registration.setId((long) result.getInt("R_ID"));

                AppointmentRepository appointmentRepository = new AppointmentRepository();
                ParticipantRepository participantRepository = new ParticipantRepository();

                registration.setAppointment(appointmentRepository.findById(result.getInt("R_A_ID")));
                registration.setParticipant(participantRepository.findById(result.getInt("R_P_ID")));

                registrations.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrations;
    }

    @Override
    public Registration findById(long id) {
        Registration registration = null;

        try (Connection connection = dataSource.getConnection()) {
            final String sql = "SELECT R_ID, R_P_ID, R_A_ID FROM CS_REGISTRATION WHERE R_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                registration = new Registration();
                registration.setId((long) result.getInt("R_ID"));

                AppointmentRepository appointmentRepository = new AppointmentRepository();
                ParticipantRepository participantRepository = new ParticipantRepository();

                registration.setAppointment(appointmentRepository.findById(result.getInt("R_A_ID")));
                registration.setParticipant(participantRepository.findById(result.getInt("R_P_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registration;
    }

    private void throwExceptionOnInvalidAppointment(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("Registration can not be null!");
        }

        if (registration.getParticipant() == null) {
            throw new IllegalArgumentException("Participant of registration can not be null!");
        }

        if (registration.getAppointment() == null) {
            throw new IllegalArgumentException("Appointment of registration can not be null!");
        }
    }
}
