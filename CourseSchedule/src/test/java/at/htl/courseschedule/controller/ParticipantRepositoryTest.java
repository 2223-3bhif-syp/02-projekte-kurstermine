package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Participant;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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
    }

    @Test
    void test_save_save_participant_ok() {
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
    void test_save_save_null_ok() {
        // arrange
        ParticipantRepository participantRepository = new ParticipantRepository();

        // act

        // assert
        assertThatCode(() -> participantRepository.save(null)).doesNotThrowAnyException();
    }

    @Test
    void test_update_update_instructor_ok() {
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
    void test_update_update_null_ok() {
        // arrange
        ParticipantRepository participantRepository = new ParticipantRepository();

        // act

        // assert
        assertThatCode(() -> participantRepository.update(null)).doesNotThrowAnyException();
    }

    @Test
    void test_insert_insert_participant_ok() {
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
    void test_insert_insert_null_ok() {
        // arrange
        ParticipantRepository participantRepository = new ParticipantRepository();

        // act

        // assert
        assertThatCode(() -> participantRepository.insert(null)).doesNotThrowAnyException();
    }

    @Test
    void test_delete_delete_inserted_participant_ok() {
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
    void test_delete_delete_null_ok() {
        // arrange
        ParticipantRepository participantRepository = new ParticipantRepository();

        // act

        // assert
        assertThatCode(() -> participantRepository.delete(null)).doesNotThrowAnyException();
    }

    @Test
    void test_delete_delete_fake_participant_ok() {
        // arrange
        ParticipantRepository participantRepository = new ParticipantRepository();
        Participant fakeParticipant = new Participant();
        fakeParticipant.setId(-1L);

        // act
        participantRepository.delete(fakeParticipant);

        // assert
        // if an error gets thrown the id does not reset to null and therefor should still be -1
        assertThat(fakeParticipant.getId()).isEqualTo(-1);
    }

    @Test
    void test_findall_find_all_inserted_participants_ok() {
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
    void test_findbyid_find_inserted_elements_ok() {
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
    void test_findbyid_search_for_id_not_in_table_ok() {
        // arrange
        ParticipantRepository parRep = new ParticipantRepository();

        // act

        // assert
        assertThat(parRep.findById(1)).isNull();
    }
}