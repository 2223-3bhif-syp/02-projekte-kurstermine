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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;

class AppointmentRepositoryTest {
    private static final String tableName = "CS_APPOINTMENT";

    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_save_save_simple_appointment_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(
                dateTime,
                instructor,
                course
        );

        // act
        appointmentRepository.save(appointment);

        output(table).toConsole();

        LocalDateTime dateTime2 = dateTime.plusHours(10);
        appointment.setStart(dateTime2);
        appointmentRepository.save(appointment);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(appointment.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(appointment.getId())
                .value().isEqualTo(appointment.getStart())
                .value().isEqualTo(appointment.getInstructor().getId())
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void test_save_instructor_and_course_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                dateTime,
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.save(appointment));
    }

    @Test
    void test_save_save_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.save(null));
    }

    @Test
    void test_update_update_appointment_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(
                dateTime,
                instructor,
                course
        );

        // act
        appointmentRepository.insert(appointment);

        output(table).toConsole();

        LocalDateTime dateTime2 = dateTime.plusHours(15);
        appointment.setStart(dateTime2);
        appointmentRepository.update(appointment);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        ); //to update the table

        output(table).toConsole();

        assertThat(appointment.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(appointment.getId())
                .value().isEqualTo(appointment.getStart())
                .value().isEqualTo(appointment.getInstructor().getId())
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void test_update_instructor_and_course_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.update(appointment));
    }

    @Test
    void test_update_update_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.update(null));
    }

    @Test
    void test_insert_insert_appointment_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        AppointmentRepository appointmentRepository = new AppointmentRepository();

        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(
                dateTime,
                instructor,
                course
        );

        // act
        appointmentRepository.insert(appointment);

        output(table).toConsole();

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(appointment.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(appointment.getId())
                .value().isEqualTo(appointment.getStart())
                .value().isEqualTo(appointment.getInstructor().getId())
                .value().isEqualTo(appointment.getCourse().getId());
    }

    @Test
    void test_insert_instructor_and_course_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.insert(appointment));
    }

    @Test
    void test_insert_insert_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.insert(null));
    }

    @Test
    void test_delete_delete_appointment_from_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        CourseRepository courseRepository = new CourseRepository();
        courseRepository.save(course);

        Appointment appointment = new Appointment(
                dateTime,
                instructor,
                course
        );

        // act
        appointmentRepository.save(appointment);
        appointmentRepository.delete(appointment);

        // assert
        assertThat(appointment.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_delete_instructor_and_course_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.update(appointment));
    }

    @Test
    void test_delete_delete_null_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentRepository.delete(null));
    }

    @Test
    void test_findall_list_contains_inserted_values_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course1);
        Course course2 = new Course(
                "Course2",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course2);
        Course course3 = new Course(
                "Course3",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(
                dateTime,
                instructor,
                course1
        );
        Appointment appointment2 = new Appointment(
                dateTime,
                instructor,
                course2
        );
        Appointment appointment3 = new Appointment(
                dateTime,
                instructor,
                course3
        );

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
    void test_findbyid_find_inserted_values_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorRepository.save(instructor);

        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course1);
        Course course2 = new Course(
                "Course2",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course2);
        Course course3 = new Course(
                "Course3",
                "Test Course",
                90,
                1
        );
        courseRepository.save(course3);

        Appointment appointment1 = new Appointment(
                dateTime,
                instructor,
                course1
        );
        Appointment appointment2 = new Appointment(
                dateTime,
                instructor,
                course2
        );
        Appointment appointment3 = new Appointment(
                dateTime,
                instructor,
                course3
        );

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
    void test_findbyid_with_id_not_in_table_ok() {
        // arrange
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        // act

        // assert
        assertThat(appointmentRepository.findById(1)).isNull();
    }
}