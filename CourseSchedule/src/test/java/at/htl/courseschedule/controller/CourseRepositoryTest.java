package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.db.api.Assertions.assertThat;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.sql.SQLException;
import java.util.List;

class CourseRepositoryTest {
    private static String tableName = "CS_COURSE";
    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
        // to clear the tables again of all the test values
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @Test
    void save() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course("Course1", "Test Course", 90, 1);

        // modify
        courseRepository.save(course);

        course.setMinutesPerAppointment(60);
        courseRepository.save(course);

        // test
        assertEquals(course.getId(), 1);

        assertThat(table).column("C_ID")
                .value().isEqualTo(course.getId());
        assertThat(table).column("C_NAME")
                .value().isEqualTo(course.getName());
        assertThat(table).column("C_DESCRIPTION")
                .value().isEqualTo(course.getDescription());
        assertThat(table).column("C_MINUTES_PER_APPOINTMENT")
                .value().isEqualTo(course.getMinutesPerAppointment());
        assertThat(table).column("C_AMOUNT_OF_APPOINTMENTS")
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void update() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course("Course1", "Test Course", 90, 1);

        // modify
        courseRepository.insert(course);

        course.setMinutesPerAppointment(60);
        courseRepository.update(course);

        // test
        assertEquals(course.getId(), 1);

        assertThat(table).column("C_ID")
                .value().isEqualTo(course.getId());
        assertThat(table).column("C_NAME")
                .value().isEqualTo(course.getName());
        assertThat(table).column("C_DESCRIPTION")
                .value().isEqualTo(course.getDescription());
        assertThat(table).column("C_MINUTES_PER_APPOINTMENT")
                .value().isEqualTo(course.getMinutesPerAppointment());
        assertThat(table).column("C_AMOUNT_OF_APPOINTMENTS")
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void insert() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course("Course1", "Test Course", 90, 1);

        // modify
        courseRepository.insert(course);

        // test
        assertEquals(course.getId(), 1);

        assertThat(table).column("C_ID")
                .value().isEqualTo(course.getId());
        assertThat(table).column("C_NAME")
                .value().isEqualTo(course.getName());
        assertThat(table).column("C_DESCRIPTION")
                .value().isEqualTo(course.getDescription());
        assertThat(table).column("C_MINUTES_PER_APPOINTMENT")
                .value().isEqualTo(course.getMinutesPerAppointment());
        assertThat(table).column("C_AMOUNT_OF_APPOINTMENTS")
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void delete() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course("Course1", "Test Course", 90, 1);

        // modify
        courseRepository.insert(course);
        courseRepository.delete(course);

        // test
        assertEquals(null, course.getId());

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void findAll() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90, 1);
        Course course2 = new Course("Course2", "Test Course", 90, 1);
        Course course3 = new Course("Course3", "Test Course", 90, 1);

        // modify
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        List<Course> courseList = courseRepository.findAll();

        // test
        assertEquals(3, courseList.size());

        assertTrue(courseList.stream().anyMatch(instructor -> course1.toString().equals(instructor.toString())));
        assertTrue(courseList.stream().anyMatch(instructor -> course2.toString().equals(instructor.toString())));
        assertTrue(courseList.stream().anyMatch(instructor -> course3.toString().equals(instructor.toString())));
    }

    @Test
    void findById() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90, 1);
        Course course2 = new Course("Course2", "Test Course", 90, 1);
        Course course3 = new Course("Course3", "Test Course", 90, 1);

        // modify
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        // test
        assertEquals(course1.toString(), courseRepository.findById(course1.getId()).toString());
        assertEquals(course2.toString(), courseRepository.findById(course2.getId()).toString());
        assertEquals(course3.toString(), courseRepository.findById(course3.getId()).toString());
    }
}