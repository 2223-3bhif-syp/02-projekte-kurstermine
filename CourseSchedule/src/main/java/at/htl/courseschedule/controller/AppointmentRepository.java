package at.htl.courseschedule.controller;

import at.htl.courseschedule.entity.Appointment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository implements Persistent<Appointment> {
    private final DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Appointment appointment) {
        throwExceptionOnInvalidAppointment(appointment);

        if (appointment.getId() == null) {
            insert(appointment);
        } else {
            update(appointment);
        }
    }

    @Override
    public void update(Appointment appointment) {
        throwExceptionOnInvalidAppointment(appointment);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE CS_APPOINTMENT SET A_START=?, A_I_ID=?, A_C_ID=? WHERE A_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
            statement.setLong(2, appointment.getInstructor().getId());
            statement.setLong(3, appointment.getCourse().getId());
            statement.setLong(4, appointment.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_APPOINTMENT failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Appointment appointment) {
        throwExceptionOnInvalidAppointment(appointment);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO CS_APPOINTMENT (A_START, A_I_ID, A_C_ID) VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
            statement.setLong(2, appointment.getInstructor().getId());
            statement.setLong(3, appointment.getCourse().getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_APPOINTMENT failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    appointment.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into CS_APPOINTMENT failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Appointment appointment) {
        throwExceptionOnInvalidAppointment(appointment);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM CS_APPOINTMENT WHERE A_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, appointment.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_APPOINTMENT failed, no rows affected");
            }
            appointment.setId(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT A_ID, A_START, A_I_ID, A_C_ID FROM CS_APPOINTMENT";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setStart(result.getTimestamp("A_START").toLocalDateTime());
                appointment.setId((long) result.getInt("A_ID"));

                InstructorRepository instructorRepository = new InstructorRepository();
                CourseRepository courseRepository = new CourseRepository();

                appointment.setInstructor(instructorRepository.findById(result.getInt("A_I_ID")));
                appointment.setCourse(courseRepository.findById(result.getInt("A_C_ID")));

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public Appointment findById(long id) {
        Appointment appointment = null;

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT A_ID, A_START, A_I_ID, A_C_ID FROM CS_APPOINTMENT WHERE A_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                appointment = new Appointment();
                appointment.setId((long) result.getInt("A_ID"));
                appointment.setStart(result.getTimestamp("A_START").toLocalDateTime());

                InstructorRepository instructorRepository = new InstructorRepository();
                CourseRepository courseRepository = new CourseRepository();

                appointment.setInstructor(instructorRepository.findById(result.getInt("A_I_ID")));
                appointment.setCourse(courseRepository.findById(result.getInt("A_C_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    private void throwExceptionOnInvalidAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment can not be null!");
        }

        if (appointment.getInstructor() == null) {
            throw new IllegalArgumentException("Instructor of appointment can not be null!");
        }

        if (appointment.getCourse() == null) {
            throw new IllegalArgumentException("Course of appointment can not be null!");
        }
    }
}
