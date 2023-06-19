package at.htl.courseschedule.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_getters_with_simple_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        LocalDateTime localDateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Appointment appointment = new Appointment(
                localDateTime,
                instructor,
                course
        );

        // act

        // assert
        assertThat(appointment.getId()).isNull();
        assertThat(appointment.getStart()).isEqualTo(localDateTime);
        assertThat(appointment.getInstructor()).usingRecursiveComparison().isEqualTo(instructor);
        assertThat(appointment.getCourse()).usingRecursiveComparison().isEqualTo(course);
    }

    @Test
    void test_getters_default_constructor_ok() {
        // arrange
        Appointment appointment = new Appointment();

        // act

        // assert
        assertThat(appointment.getId()).isNull();
        assertThat(appointment.getStart()).isNull();
        assertThat(appointment.getInstructor()).isNull();
        assertThat(appointment.getCourse()).isNull();
    }

    @Test
    void test_setters_with_default_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        LocalDateTime localDateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Appointment appointment = new Appointment();

        // act
        appointment.setId(10L);
        appointment.setStart(localDateTime);
        appointment.setInstructor(instructor);
        appointment.setCourse(course);

        // assert
        assertThat(appointment.getId()).isEqualTo(10L);
        assertThat(appointment.getStart()).isEqualTo(localDateTime);
        assertThat(appointment.getInstructor()).usingRecursiveComparison().isEqualTo(instructor);
        assertThat(appointment.getCourse()).usingRecursiveComparison().isEqualTo(course);
    }

    @Test
    void test_setters_simple_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        LocalDateTime localDateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Appointment appointment = new Appointment(
                localDateTime,
                instructor,
                course
        );
        Course newCourse = new Course(
                "Testing123",
                "This is illegal",
                90,
                3
        );
        Instructor newInstructor = new Instructor(
                "Theodor",
                "Grau",
                "+48 342 0323244",
                "grauTheo@yahoomail.com"
        );
        LocalDateTime newDateTime = localDateTime.minusDays(4);

        // act
        appointment.setId(12L);
        appointment.setStart(newDateTime);
        appointment.setInstructor(newInstructor);
        appointment.setCourse(newCourse);

        // assert
        assertThat(appointment.getId()).isEqualTo(12L);
        assertThat(appointment.getStart()).isEqualTo(newDateTime);
        assertThat(appointment.getInstructor()).usingRecursiveComparison().isEqualTo(newInstructor);
        assertThat(appointment.getCourse()).usingRecursiveComparison().isEqualTo(newCourse);
    }

    @Test
    void test_setters_and_change_values_of_references_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        LocalDateTime localDateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Appointment appointment = new Appointment(
                localDateTime,
                instructor,
                course
        );

        // act
        appointment.getCourse().setName("Aaaaaaaaaaaaaa");
        appointment.getCourse().setDescription("This is a test");
        appointment.getCourse().setMinutesPerAppointment(43);
        appointment.getCourse().setAmountOfAppointments(2);
        appointment.getInstructor().setLastName("Berger");

        // assert
        assertThat(appointment.getId()).isNull();
        assertThat(appointment.getStart()).isEqualTo(localDateTime);
        assertThat(appointment.getInstructor()).usingRecursiveComparison().isEqualTo(instructor);
        assertThat(appointment.getCourse()).usingRecursiveComparison().isEqualTo(course);
        assertThat(appointment.getCourse()).isNotNull().satisfies(c -> {
            assertThat(c.getName()).isEqualTo("Aaaaaaaaaaaaaa");
            assertThat(c.getDescription()).isEqualTo("This is a test");
            assertThat(c.getMinutesPerAppointment()).isEqualTo(43);
            assertThat(c.getAmountOfAppointments()).isEqualTo(2);
        });
        assertThat(appointment.getInstructor().getLastName()).isEqualTo("Berger");
    }
}