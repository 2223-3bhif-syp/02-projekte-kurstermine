package at.htl.courseschedule.controller;

import at.htl.courseschedule.entity.Course;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements Persistent<Course> {
    private final DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Course course) {
        if (course.getId() == null) {
            insert(course);
        }
        else {
            update(course);
        }
    }

    @Override
    public void update(Course course) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE CS_COURSE SET C_NAME=?, C_DESCRIPTION=?, C_MINUTES_PER_APPOINTMENT=?, C_AMOUNT_OF_APPOINTMENTS=? WHERE C_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getMinutesPerAppointment());
            statement.setInt(4, course.getAmountOfAppointments());
            statement.setLong(5, course.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_COURSE failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Course course) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO CS_COURSE (C_NAME, C_DESCRIPTION, C_MINUTES_PER_APPOINTMENT, C_AMOUNT_OF_APPOINTMENTS) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getMinutesPerAppointment());
            statement.setInt(4, course.getAmountOfAppointments());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_COURSE failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    course.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into CS_COURSE failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM CS_COURSE WHERE C_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of CS_COURSE failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM CS_COURSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()) {
                Course course = new Course(result.getString("C_NAME"),
                        result.getString("C_DESCRIPTION"), result.getInt("C_MINUTES_PER_APPOINTMENT"),
                        result.getInt("C_AMOUNT_OF_APPOINTMENTS"));

                course.setId((long)result.getInt("C_ID"));

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public Course findById(long id) {
        Course course = null;

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM CS_COURSE WHERE C_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                course = new Course(result.getString("C_NAME"), result.getString("C_DESCRIPTION"),
                        result.getInt("C_MINUTES_PER_APPOINTMENT"), result.getInt("C_AMOUNT_OF_APPOINTMENTS"));
                course.setId((long)result.getInt("C_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }
}
