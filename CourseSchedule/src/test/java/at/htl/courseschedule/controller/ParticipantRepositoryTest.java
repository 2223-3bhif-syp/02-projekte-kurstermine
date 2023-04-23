package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Participant;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

class ParticipantRepositoryTest {
    private static final String tableName = "CS_PARTICIPANT";

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
    void test_save_SaveSimpleParticipant_ShouldResultInDatabaseRowWithValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act
        parRep.save(participant);

        participant.setEmail("newEmail@gmail.com");
        parRep.save(participant);

        // assert
        assertThat(participant.getId()).isEqualTo(1);

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
    void test_update_SimpleUpdate_ShouldUpdateValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act
        parRep.insert(participant);

        participant.setEmail("newEmail@gmail.com");
        parRep.update(participant);

        // assert
        assertThat(participant.getId()).isEqualTo(1);

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
    void test_insert_SimpleInsert_ShouldAddValuesToDatabase() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act
        parRep.insert(participant);

        // assert
        assertThat(participant.getId()).isEqualTo(1);

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
    void test_delete_SimpleDelete_ShouldRemoveValues() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.insert(participant);
        parRep.delete(participant);

        // test
        assertThat(participant.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_findAll_SimpleInsertAndFind_ShouldFindInsertedValues() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant1 = new Participant("1", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");
        Participant participant2 = new Participant("2", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");
        Participant participant3 = new Participant("3", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act
        parRep.save(participant1);
        parRep.save(participant2);
        parRep.save(participant3);

        List<Participant> participantList = parRep.findAll();

        // assert
        assertThat(participantList).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(participant1, participant2, participant3);
    }

    @Test
    void test_findById_SimpleInsertAndFind_ShouldFindValues() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();
        Participant participant1 = new Participant("1", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");
        Participant participant2 = new Participant("2", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");
        Participant participant3 = new Participant("3", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // modify
        parRep.save(participant1);
        parRep.save(participant2);
        parRep.save(participant3);

        // test
        assertThat(parRep.findById(participant1.getId())).usingRecursiveComparison().isEqualTo(participant1);
        assertThat(parRep.findById(participant2.getId())).usingRecursiveComparison().isEqualTo(participant2);
        assertThat(parRep.findById(participant3.getId())).usingRecursiveComparison().isEqualTo(participant3);
    }

    @Test
    void test_findById_whenNotInTables() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();

        // act

        // assert
        assertThat(parRep.findById(1)).isNull();
    }
}