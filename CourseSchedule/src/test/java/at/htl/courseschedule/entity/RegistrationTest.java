package at.htl.courseschedule.entity;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.db.api.Assertions.assertThat;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class RegistrationTest {
    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testToString() {
        // arrange
        Participant participant = new Participant("firstName", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        LocalDateTime dateTime = LocalDateTime.now();
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");
        Course course = new Course("Course1", "Test Course", 90, 1);
        Appointment appointment = new Appointment(dateTime, instructor, course);

        Registration registration = new Registration(participant, appointment);

        // modify

        // test
        assertEquals("Registration{participantId=Participant{id=null, firstName='firstName', lastName='lastName', yearOfBirth=2000, phoneNr='+43 6704070789', email='lastName@gmail.com'}, appointmentId=Appointment{id=null, start=" + dateTime.toString() + ", instructorId=Instructor{id=null, firstName='firstName', lastName='lastName', phoneNr='+43 6704070789', email='lastName@gmail.com'}, courseId=Course{id=null, name='Course1', description='Test Course', minutesPerAppointment=90, amountOfAppointments=1}}}", registration.toString());
    }
}