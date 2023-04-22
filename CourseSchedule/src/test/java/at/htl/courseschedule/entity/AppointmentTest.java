package at.htl.courseschedule.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import net.bytebuddy.asm.Advice;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.descriptor.TestInstanceLifecycleUtils;

import java.time.LocalDateTime;

class AppointmentTest {
    Instructor instructor;
    LocalDateTime localDateTime;
    Course course;

    @BeforeEach
    public void setUp() {
        instructor = new Instructor("firstName", "lastName", "+43 6704070789",
                "lastName@gmail.com");
        localDateTime = LocalDateTime.now();
        course = new Course("Course1", "Test Course", 90,
            1);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_ToString_SimpleCtor_ShouldShowEnteredValues() {
        // arrange
        Appointment appointment = new Appointment(localDateTime, instructor, course);

        // act

        // assert
        assertThat(appointment.toString())
                .isEqualTo("Appointment{id=null, start=" + localDateTime.toString() + ", instructorId=Instructor{id=null, firstName='firstName', lastName='lastName', phoneNr='+43 6704070789', email='lastName@gmail.com'}, courseId=Course{id=null, name='Course1', description='Test Course', minutesPerAppointment=90, amountOfAppointments=1}}");
    }

    @Test
    void test_Getters_SimpleCtor_ShouldReturnEnteredValues() {
        // arrange
        Appointment appointment = new Appointment(localDateTime, instructor, course);

        // act

        // assert
        assertThat(appointment.getId()).isNull();
        assertThat(appointment.getStart()).isEqualTo(localDateTime);
        assertThat(appointment.getInstructor()).usingRecursiveComparison().isEqualTo(instructor);
        assertThat(appointment.getCourse()).usingRecursiveComparison().isEqualTo(course);
    }

    @Test
    void test_Getters_DefaultCtor_ShouldReturnDefaultValues() {
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
    void test_Setters_DefaultCtor_ShouldSetValuesAccordingly() {
        // arrange
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
    void test_Setters_SimpleCtor_ShouldChangeValuesAccordingly() {
        // arrange
        Appointment appointment = new Appointment(localDateTime, instructor, course);
        Course newCourse = new Course("Testing123", "This is illegal", 90,
                3);
        Instructor newInstructor = new Instructor("Theodor", "Grau", "+48 342 0323244",
                "grauTheo@yahoomail.com");
        LocalDateTime newDateTime = LocalDateTime.now().minusDays(4);

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
    void test_Setters_ChangeReferenceValues_ShouldChangeValuesAccordingly() {
        // arrange
        Appointment appointment = new Appointment(localDateTime, instructor, course);

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