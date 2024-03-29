package at.htl.courseschedule.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationTest {
    Participant participant;
    Appointment appointment;
    LocalDateTime localDateTime;

    @BeforeEach
    public void setUp() {
        localDateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Instructor instructor = new Instructor(
                "InstructorName",
                "Leitner",
                "+43 6704070789",
                "LeiterName@speedmail.com"
        );
        Course course = new Course(
                "Amselarbeit",
                "Die Arbeit, sie ruft",
                180,
                45
        );

        participant = new Participant(
                "Michael",
                "Blauberg",
                2010,
                "+43 323 2314809",
                "blaubergMich@gmail.com"
        );
        appointment = new Appointment(
                localDateTime,
                instructor,
                course
        );
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_getters_with_simple_constructor_ok() {
        // arrange
        Registration registration = new Registration(
                participant,
                appointment
        );

        // act

        // assert
        assertThat(registration.getAppointment()).usingRecursiveComparison().isEqualTo(appointment);
        assertThat(registration.getParticipant()).usingRecursiveComparison().isEqualTo(participant);
    }

    @Test
    void test_getters_with_default_constructor_ok() {
        // arrange
        Registration registration = new Registration();

        // act

        // assert
        assertThat(registration.getAppointment()).isNull();
        assertThat(registration.getParticipant()).isNull();
    }

    @Test
    void test_setters_with_simple_constructor_ok() {
        // arrange
        Registration registration = new Registration(
                participant,
                appointment
        );

        LocalDateTime newLocalDateTime = localDateTime.plusDays(18);
        Instructor newInstructor = new Instructor(
                "I am new",
                "New",
                "+43 2342343245",
                "newmail@speedmail.com"
        );
        Course newCourse = new Course(
                "Speedster",
                "Wer bremst verliert",
                200,
                85
        );
        Participant newParticipant = new Participant(
                "Winnie",
                "Ilming",
                2006,
                "+43 234 234243",
                "subbortyesyes@gmail.com"
        );
        Appointment newAppointment = new Appointment(
                newLocalDateTime,
                newInstructor,
                newCourse
        );

        // act
        registration.setAppointment(newAppointment);
        registration.setParticipant(newParticipant);

        // assert
        assertThat(registration.getAppointment()).usingRecursiveComparison().isEqualTo(newAppointment);
        assertThat(registration.getParticipant()).usingRecursiveComparison().isEqualTo(newParticipant);
    }

    @Test
    void test_setters_with_default_constructor_ok() {
        // arrange
        Registration registration = new Registration();

        // act
        registration.setParticipant(participant);
        registration.setAppointment(appointment);

        // assert
        assertThat(registration.getAppointment()).usingRecursiveComparison().isEqualTo(appointment);
        assertThat(registration.getParticipant()).usingRecursiveComparison().isEqualTo(participant);
    }
}