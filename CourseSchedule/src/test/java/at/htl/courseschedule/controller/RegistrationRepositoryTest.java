package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.*;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;

public class RegistrationRepositoryTest {
    private static final String tableName = "CS_REGISTRATION";

    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_save_save_simple_registration_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                instructor,
                course
        );
        Registration registration = new Registration(
                participant,
                appointment
        );

        participantRepository.save(participant);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);

        // act
        registrationRepository.save(registration);

        // assert
        output(table).toConsole();

        assertThat(registration.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(registration.getId())
                .value().isEqualTo(appointment.getId())
                .value().isEqualTo(participant.getId());
    }

    @Test
    void test_save_participant_and_appointment_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();
        Registration registration = new Registration(
                null,
                null);

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.save(registration));

    }

    @Test
    void test_save_save_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.save(null));
    }

    @Test
    void test_update_update_registration_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Participant participant2 = new Participant(
                "Florian",
                "Mühle",
                1990,
                "+43 681 234987",
                "fmuehle@gmail.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime dateTime = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                dateTime,
                instructor,
                course
        );
        Appointment appointment2 = new Appointment(
                dateTime.plusHours(5),
                instructor,
                course
        );
        Registration registration = new Registration(
                participant,
                appointment
        );

        participantRepository.save(participant);
        participantRepository.save(participant2);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);
        appointmentRepository.save(appointment2);
        registrationRepository.save(registration);

        // act
        output(table).toConsole();

        registration.setParticipant(participant2);
        registration.setAppointment(appointment2);

        registrationRepository.update(registration);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(registration.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(registration.getId())
                .value().isEqualTo(appointment2.getId())
                .value().isEqualTo(participant2.getId());
    }

    @Test
    void test_update_participant_and_appointment_null_ok() {
        /// arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();
        Registration registration = new Registration(
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.update(registration));

    }

    @Test
    void test_update_update_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.update(null));
    }

    @Test
    void test_insert_insert_appointment_and_check_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                instructor,
                course
        );
        Registration registration = new Registration(
                participant,
                appointment
        );

        participantRepository.save(participant);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);

        // act
        registrationRepository.insert(registration);

        // assert
        output(table).toConsole();

        assertThat(registration.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(registration.getId())
                .value().isEqualTo(appointment.getId())
                .value().isEqualTo(participant.getId());
    }

    @Test
    void test_insert_participant_and_appointment_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();
        Registration registration = new Registration(
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.insert(registration));
    }

    @Test
    void test_insert_insert_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.insert(null));
    }

    @Test
    void test_delete_delete_registration_from_database_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(now, instructor, course);
        Registration registration = new Registration(participant, appointment);

        participantRepository.save(participant);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);
        registrationRepository.save(registration);

        // act
        registrationRepository.delete(registration);

        // assert
        assertThat(registration.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_delete_participant_and_appointment_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();
        Registration registration = new Registration(
                null,
                null
        );

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.delete(registration));
    }

    @Test
    void test_delete_delete_null_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> registrationRepository.delete(null));
    }

    @Test
    void test_findall_list_contains_inserted_values_ok() {
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Participant participant2 = new Participant(
                "Florian",
                "Mühle",
                1990,
                "+43 681 234987",
                "fmuehle@gmail.com"
        );
        Participant participant3 = new Participant(
                "Kilian",
                "Brand",
                2001,
                "+43 681 324678482",
                "kilibrnd@gmail.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                instructor,
                course
        );
        Appointment appointment2 = new Appointment(
                now.plusHours(5),
                instructor,
                course
        );
        Appointment appointment3 = new Appointment(
                now.plusHours(10),
                instructor,
                course
        );
        Registration registration = new Registration(
                participant,
                appointment
        );
        Registration registration2 = new Registration(
                participant2,
                appointment2
        );
        Registration registration3 = new Registration(
                participant3,
                appointment3
        );

        participantRepository.save(participant);
        participantRepository.save(participant2);
        participantRepository.save(participant3);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);
        registrationRepository.save(registration);
        registrationRepository.save(registration2);
        registrationRepository.save(registration3);

        // act
        List<Registration> registrations = registrationRepository.findAll();

        // assert
        assertThat(registrations).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(registration, registration2, registration3);
    }

    @Test
    void test_findbyid_find_inserted_values_ok() {
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );
        RegistrationRepository registrationRepository = new RegistrationRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        CourseRepository courseRepository = new CourseRepository();
        InstructorRepository instructorRepository = new InstructorRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();

        Participant participant = new Participant(
                "Hans",
                "Müller",
                1997,
                "+43 681 2340234085",
                "hansmueller@gmail.com"
        );
        Instructor instructor = new Instructor(
                "Franz",
                "Huber",
                "+43 770 232342877",
                "fhuber@yahoo.com"
        );
        Participant participant2 = new Participant(
                "Florian",
                "Mühle",
                1990,
                "+43 681 234987",
                "fmuehle@gmail.com"
        );
        Participant participant3 = new Participant(
                "Kilian",
                "Brand",
                2001,
                "+43 681 324678482",
                "kilibrnd@gmail.com"
        );
        Course course = new Course(
                "Example",
                "Lorem ipsum",
                50,
                10
        );
        LocalDateTime now = LocalDateTime.of(
                2023,
                5,
                26,
                12,
                30
        );
        Appointment appointment = new Appointment(
                now,
                instructor,
                course
        );
        Appointment appointment2 = new Appointment(
                now.plusHours(5),
                instructor,
                course
        );
        Appointment appointment3 = new Appointment(
                now.plusHours(10),
                instructor,
                course
        );
        Registration registration = new Registration(
                participant,
                appointment
        );
        Registration registration2 = new Registration(
                participant2,
                appointment2
        );
        Registration registration3 = new Registration(
                participant3,
                appointment3
        );

        participantRepository.save(participant);
        participantRepository.save(participant2);
        participantRepository.save(participant3);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        appointmentRepository.save(appointment);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);
        registrationRepository.save(registration);
        registrationRepository.save(registration2);
        registrationRepository.save(registration3);

        // act
        Registration repositoryRegistration1 = registrationRepository.findById(registration.getId());
        Registration repositoryRegistration2 = registrationRepository.findById(registration2.getId());
        Registration repositoryRegistration3 = registrationRepository.findById(registration3.getId());

        // assert
        assertThat(repositoryRegistration1).usingRecursiveComparison()
                .isEqualTo(registration);
        assertThat(repositoryRegistration2).usingRecursiveComparison()
                .isEqualTo(registration2);
        assertThat(repositoryRegistration3).usingRecursiveComparison()
                .isEqualTo(registration3);
    }

    @Test
    void test_findbyid_with_id_not_in_table_ok() {
        // arrange
        RegistrationRepository registrationRepository = new RegistrationRepository();

        // act

        // assert
        assertThat(registrationRepository.findById(1)).isNull();
    }
}
