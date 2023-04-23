package at.htl.courseschedule.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_ToString_AfterNormalCtor_ShouldResultInEnteredValuesAndIdNull() {
        // arrange
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act

        // assert
        assertThat(participant.toString())
                .isEqualTo("Participant{id=null, firstName='firstName', lastName='lastName', yearOfBirth=2000, phoneNr='+43 6704070789', email='lastName@gmail.com'}");
    }

    @Test
    void test_Getters_AfterNormalCtor_ShouldResultInEnteredValuesAndIdNull() {
        // arrange
        Participant participant = new Participant("firstName", "lastName", 2000,
                "+43 6704070789", "lastName@gmail.com");

        // act

        // assert
        assertThat(participant.getId()).isNull();
        assertThat(participant.getEmail()).isEqualTo("lastName@gmail.com");
        assertThat(participant.getFirstName()).isEqualTo("firstName");
        assertThat(participant.getLastName()).isEqualTo("lastName");
        assertThat(participant.getYearOfBirth()).isEqualTo(2000);
        assertThat(participant.getPhoneNr()).isEqualTo("+43 6704070789");
    }

    @Test
    void test_Getters_AfterDefaultCtor_ShouldResultInDefaultValues() {
        // arrange
        Participant participant = new Participant();

        // act

        // assert
        assertThat(participant.getId()).isNull();
        assertThat(participant.getEmail()).isNull();
        assertThat(participant.getFirstName()).isNull();
        assertThat(participant.getLastName()).isNull();
        assertThat(participant.getYearOfBirth()).isEqualTo(0);
        assertThat(participant.getPhoneNr()).isNull();
    }

    @Test
    void test_Setters_SimpleChangesOfDefaultValues_ShouldResultInNewValues() {
        // arrange
        Participant participant = new Participant();

        // act
        participant.setId(0L);
        participant.setEmail("something@example.com");
        participant.setFirstName("Hans");
        participant.setLastName("Müller");
        participant.setPhoneNr("+43 681 2202234");
        participant.setYearOfBirth(2010);


        // assert
        assertThat(participant.getId()).isEqualTo(0L);
        assertThat(participant.getEmail()).isEqualTo("something@example.com");
        assertThat(participant.getFirstName()).isEqualTo("Hans");
        assertThat(participant.getLastName()).isEqualTo("Müller");
        assertThat(participant.getYearOfBirth()).isEqualTo(2010);
        assertThat(participant.getPhoneNr()).isEqualTo("+43 681 2202234");
    }

    @Test
    void test_Setters_SimpleChangesOfCtorSetValues_ShouldResultInNewValues() {
        // arrange
        Participant participant = new Participant("Anita", "Huber", 1999,
                "+43 681 0304877", "anitaHuber@gmail.com");

        // act
        participant.setId(23L);
        participant.setEmail("iexist@yahoomail.com");
        participant.setFirstName("Christoph");
        participant.setLastName("Ilming");
        participant.setPhoneNr("+43 722 23409978");
        participant.setYearOfBirth(2011);


        // assert
        assertThat(participant.getId()).isEqualTo(23L);
        assertThat(participant.getEmail()).isEqualTo("iexist@yahoomail.com");
        assertThat(participant.getFirstName()).isEqualTo("Christoph");
        assertThat(participant.getLastName()).isEqualTo("Ilming");
        assertThat(participant.getYearOfBirth()).isEqualTo(2011);
        assertThat(participant.getPhoneNr()).isEqualTo("+43 722 23409978");
    }
}