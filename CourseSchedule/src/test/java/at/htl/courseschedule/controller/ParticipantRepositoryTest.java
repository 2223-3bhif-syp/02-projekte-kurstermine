package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Instructor;
import at.htl.courseschedule.entity.Participant;
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

class ParticipantRepositoryTest {
    private static String tableName = "CS_PARTICIPANT";
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

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.save(participant);

        participant.setEmail("newEmail@gmail.com");
        parRep.save(participant);

        // test
        assertEquals(participant.getId(), 1);

        assertThat(table).column("P_ID")
                .value().isEqualTo(participant.getId());
        assertThat(table).column("P_FIRST_NAME")
                .value().isEqualTo(participant.getFirstName());
        assertThat(table).column("P_LAST_NAME")
                .value().isEqualTo(participant.getLastName());
        assertThat(table).column("P_YEAR_OF_BIRTH")
                .value().isEqualTo(participant.getYearOfBirth());
        assertThat(table).column("P_PHONE_NR")
                .value().isEqualTo(participant.getPhoneNr());
        assertThat(table).column("P_EMAIL")
                .value().isEqualTo(participant.getEmail());
    }

    @Test
    void update() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.insert(participant);

        participant.setEmail("newEmail@gmail.com");
        parRep.update(participant);

        // test
        assertEquals(participant.getId(), 1);

        assertThat(table).column("P_ID")
                .value().isEqualTo(participant.getId());
        assertThat(table).column("P_FIRST_NAME")
                .value().isEqualTo(participant.getFirstName());
        assertThat(table).column("P_LAST_NAME")
                .value().isEqualTo(participant.getLastName());
        assertThat(table).column("P_YEAR_OF_BIRTH")
                .value().isEqualTo(participant.getYearOfBirth());
        assertThat(table).column("P_PHONE_NR")
                .value().isEqualTo(participant.getPhoneNr());
        assertThat(table).column("P_EMAIL")
                .value().isEqualTo(participant.getEmail());
    }

    @Test
    void insert() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.insert(participant);

        // test
        assertEquals(participant.getId(), 1);

        assertThat(table).column("P_ID")
                .value().isEqualTo(participant.getId());
        assertThat(table).column("P_FIRST_NAME")
                .value().isEqualTo(participant.getFirstName());
        assertThat(table).column("P_LAST_NAME")
                .value().isEqualTo(participant.getLastName());
        assertThat(table).column("P_YEAR_OF_BIRTH")
                .value().isEqualTo(participant.getYearOfBirth());
        assertThat(table).column("P_PHONE_NR")
                .value().isEqualTo(participant.getPhoneNr());
        assertThat(table).column("P_EMAIL")
                .value().isEqualTo(participant.getEmail());
    }

    @Test
    void delete() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.insert(participant);
        parRep.delete(participant);

        // test
        assertEquals(null, participant.getId());

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void findAll() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant1 = new Participant("1", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");
        Participant participant2 = new Participant("2", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");
        Participant participant3 = new Participant("3", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.save(participant1);
        parRep.save(participant2);
        parRep.save(participant3);

        List<Participant> participantList = parRep.findAll();

        // test
        assertEquals(3, participantList.size());

        assertTrue(participantList.stream().anyMatch(instructor -> participant1.toString().equals(instructor.toString())));
        assertTrue(participantList.stream().anyMatch(instructor -> participant2.toString().equals(instructor.toString())));
        assertTrue(participantList.stream().anyMatch(instructor -> participant3.toString().equals(instructor.toString())));
    }

    @Test
    void findById() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant1 = new Participant("1", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");
        Participant participant2 = new Participant("2", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");
        Participant participant3 = new Participant("3", "lastName", 2000, "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.save(participant1);
        parRep.save(participant2);
        parRep.save(participant3);

        // test
        assertEquals(participant1.toString(), parRep.findById(participant1.getId()).toString());
        assertEquals(participant2.toString(), parRep.findById(participant2.getId()).toString());
        assertEquals(participant3.toString(), parRep.findById(participant3.getId()).toString());
    }

    @Test
    void test_findById_whenNotInTables() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();

        // act

        // assert
        assertNull(parRep.findById(1));
    }
}