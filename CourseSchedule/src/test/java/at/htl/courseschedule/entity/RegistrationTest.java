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
        localDateTime = LocalDateTime.now();
        Instructor instructor = new Instructor("InstructorName", "Leitner",
                "+43 6704070789", "LeiterName@speedmail.com");
        Course course = new Course("Amselarbeit", "Die Arbeit, sie ruft", 180,
                45);

        participant = new Participant("Michael", "Blauberg", 2010,
                "+43 323 2314809", "blaubergMich@gmail.com");
        appointment = new Appointment(localDateTime, instructor, course);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_ToString_SimpleCtor_ShouldResultInStringWithCtorValues() {
        // arrange
        Registration registration = new Registration(participant, appointment);

        // act

        // assert
        assertThat(registration.toString())
                .isEqualTo("Registration{participantId=Participant{id=null, firstName='Michael', lastName='Blauberg', yearOfBirth=2010, phoneNr='+43 323 2314809', email='blaubergMich@gmail.com'}, appointmentId=Appointment{id=null, start=" + localDateTime.toString() + ", instructorId=Instructor{id=null, firstName='InstructorName', lastName='Leitner', phoneNr='+43 6704070789', email='LeiterName@speedmail.com'}, courseId=Course{id=null, name='Amselarbeit', description='Die Arbeit, sie ruft', minutesPerAppointment=180, amountOfAppointments=45}}}");
    }

    @Test
    void test_Getters_SimpleCtor_ShouldResultInEnteredValues() {
        // arrange
        Registration registration = new Registration(participant, appointment);

        // act

        // assert
        assertThat(registration.getAppointment()).usingRecursiveComparison().isEqualTo(appointment);
        assertThat(registration.getParticipant()).usingRecursiveComparison().isEqualTo(participant);
    }

    @Test
    void test_Getters_DefaultCtor_ShouldResultInDefaultValues() {
        // arrange
        Registration registration = new Registration();

        // act

        // assert
        assertThat(registration.getAppointment()).isNull();
        assertThat(registration.getParticipant()).isNull();
    }

    @Test
    void test_Setters_SimpleCtor_ShouldChangeValuesAccordingly() {
        // arrange
        Registration registration = new Registration(participant, appointment);

        LocalDateTime newLocalDateTime = LocalDateTime.now().plusDays(18);
        Instructor newInstructor = new Instructor("I am new", "New", "+43 2342343245",
                "newmail@speedmail.com");
        Course newCourse = new Course("Speedster", "Wer bremst verliert", 200,
                85);
        Participant newParticipant = new Participant("Winnie", "Ilming", 2006,
                "+43 234 234243", "subbortyesyes@gmail.com");
        Appointment newAppointment = new Appointment(newLocalDateTime, newInstructor, newCourse);

        // act
        registration.setAppointment(newAppointment);
        registration.setParticipant(newParticipant);

        // assert
        assertThat(registration.getAppointment()).usingRecursiveComparison().isEqualTo(newAppointment);
        assertThat(registration.getParticipant()).usingRecursiveComparison().isEqualTo(newParticipant);
    }

    @Test
    void test_Setters_DefaultCtor_ShouldResultInNewValues() {
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