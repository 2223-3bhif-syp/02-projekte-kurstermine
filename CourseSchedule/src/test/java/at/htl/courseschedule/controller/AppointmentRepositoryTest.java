package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Appointment;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

class AppointmentRepositoryTest {
    private static String tableName = "CS_APPOINTMENT";
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

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90, 1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // modify
        appointmentRepository.save(appointment);

        String dateString2 = "16-08-2023 00:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);
        appointment.setStart(dateTime2);
        appointmentRepository.save(appointment);

        // test
        assertEquals(appointment.getId(), 1);

        assertThat(table).column("A_ID")
                .value().isEqualTo(appointment.getId());
        assertThat(table).column("A_START")
                .value().isEqualTo(appointment.getStart());
        assertThat(table).column("A_I_ID")
                .value().isEqualTo(appointment.getInstructor().getId());
        assertThat(table).column("A_C_ID")
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void update() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90, 1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // modify
        appointmentRepository.insert(appointment);

        String dateString2 = "17-09-2023 00:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);
        appointment.setStart(dateTime2);
        appointmentRepository.update(appointment);

        // test
        assertEquals(appointment.getId(), 1);

        assertThat(table).column("A_ID")
                .value().isEqualTo(appointment.getId());
        assertThat(table).column("A_START")
                .value().isEqualTo(appointment.getStart());
        assertThat(table).column("A_I_ID")
                .value().isEqualTo(appointment.getInstructor().getId());
        assertThat(table).column("A_C_ID")
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void insert() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90, 1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // modify
        appointmentRepository.insert(appointment);

        // test
        assertEquals(appointment.getId(), 1);

        assertThat(table).column("A_ID")
                .value().isEqualTo(appointment.getId());
        assertThat(table).column("A_START")
                .value().isEqualTo(appointment.getStart());
        assertThat(table).column("A_I_ID")
                .value().isEqualTo(appointment.getInstructor().getId());
        assertThat(table).column("A_C_ID")
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void delete() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90, 1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // modify
        appointmentRepository.save(appointment);
        appointmentRepository.delete(appointment);

        // test
        assertEquals(null, appointment.getId());

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void findAll() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90, 1);
        courseRepository.save(course1);
        Course course2 = new Course("Course2", "Test Course", 90, 1);
        courseRepository.save(course2);
        Course course3 = new Course("Course3", "Test Course", 90, 1);
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(dateTime, instructor, course1);
        Appointment appointment2 = new Appointment(dateTime, instructor, course2);
        Appointment appointment3 = new Appointment(dateTime, instructor, course3);

        // modify
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> appointmentList = appointmentRepository.findAll();

        // test
        assertEquals(3, appointmentList.size());

        assertTrue(appointmentList.stream().anyMatch(appointment -> appointment1.toString().equals(appointment.toString())));
        assertTrue(appointmentList.stream().anyMatch(appointment -> appointment2.toString().equals(appointment.toString())));
        assertTrue(appointmentList.stream().anyMatch(appointment -> appointment3.toString().equals(appointment.toString())));
    }

    @Test
    void findById() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90, 1);
        courseRepository.save(course1);
        Course course2 = new Course("Course2", "Test Course", 90, 1);
        courseRepository.save(course2);
        Course course3 = new Course("Course3", "Test Course", 90, 1);
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(dateTime, instructor, course1);
        Appointment appointment2 = new Appointment(dateTime, instructor, course2);
        Appointment appointment3 = new Appointment(dateTime, instructor, course3);

        // modify
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        // test
        assertEquals(appointment1.toString(), appointmentRepository.findById(appointment1.getId()).toString());
        assertEquals(appointment2.toString(), appointmentRepository.findById(appointment2.getId()).toString());
        assertEquals(appointment3.toString(), appointmentRepository.findById(appointment3.getId()).toString());
    }
}