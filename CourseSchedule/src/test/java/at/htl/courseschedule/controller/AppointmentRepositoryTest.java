package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

class AppointmentRepositoryTest {
    private static final String tableName = "CS_APPOINTMENT";

    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
        // to clear the tables again of all the test values
        //SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @Test
    void test_save_SaveSimpleAppointment_ShouldResultInDatabaseRowWithValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90,
                1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // act
        appointmentRepository.save(appointment);

        String dateString2 = "16-08-2023 00:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);
        appointment.setStart(dateTime2);
        appointmentRepository.save(appointment);

        // assert
        assertThat(appointment.getId()).isEqualTo(1);

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
    void test_update_SimpleUpdate_ShouldUpdateValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90,
                1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // act
        appointmentRepository.insert(appointment);

        String dateString2 = "17-09-2023 00:00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);
        appointment.setStart(dateTime2);
        appointmentRepository.update(appointment);

        // assert
        assertThat(appointment.getId()).isEqualTo(1);

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
    void test_insert_SimpleInsert_ShouldAddValuesToDatabase() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90,
                1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // act
        appointmentRepository.insert(appointment);

        // assert
        assertThat(appointment.getId()).isEqualTo(1);

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
    void test_delete_SimpleDelete_ShouldRemoveValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course("Course1", "Test Course", 90,
                1);
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(dateTime, instructor, course);

        // act
        appointmentRepository.save(appointment);
        appointmentRepository.delete(appointment);

        // assert
        assertThat(appointment.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_delete_DeleteFakeAppointment_ShouldThrowError() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        Appointment fakeAppointment = new Appointment();
        fakeAppointment.setId(-1L);

        // act
        appointmentRepository.delete(fakeAppointment);

        // assert
        // if an error gets thrown the id does not reset to null and therefor should still be -1
        assertThat(fakeAppointment.getId()).isEqualTo(-1);
    }

    @Test
    void test_findAll_SimpleInsertAndFind_ShouldFindInsertedValues() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90,
                1);
        courseRepository.save(course1);
        Course course2 = new Course("Course2", "Test Course", 90,
                1);
        courseRepository.save(course2);
        Course course3 = new Course("Course3", "Test Course", 90,
                1);
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(dateTime, instructor, course1);
        Appointment appointment2 = new Appointment(dateTime, instructor, course2);
        Appointment appointment3 = new Appointment(dateTime, instructor, course3);

        // act
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> appointmentList = appointmentRepository.findAll();

        // assert
        assertThat(appointmentList).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(appointment1, appointment2, appointment3);
    }

    @Test
    void test_findById_SimpleInsertAndFind_ShouldFindValues() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        String dateString = "07-08-2023 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ROOT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course("Course1", "Test Course", 90,
                1);
        courseRepository.save(course1);
        Course course2 = new Course("Course2", "Test Course", 90,
                1);
        courseRepository.save(course2);
        Course course3 = new Course("Course3", "Test Course", 90,
                1);
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(dateTime, instructor, course1);
        Appointment appointment2 = new Appointment(dateTime, instructor, course2);
        Appointment appointment3 = new Appointment(dateTime, instructor, course3);

        // act
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        // assert
        assertThat(appointmentRepository.findById(appointment1.getId())).usingRecursiveComparison()
                .isEqualTo(appointment1);
        assertThat(appointmentRepository.findById(appointment2.getId())).usingRecursiveComparison()
                .isEqualTo(appointment2);
        assertThat(appointmentRepository.findById(appointment3.getId())).usingRecursiveComparison()
                .isEqualTo(appointment3);
    }

    @Test
    void test_findById_whenNotInTables() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThat(appointmentRepository.findById(1)).isNull();
    }
}