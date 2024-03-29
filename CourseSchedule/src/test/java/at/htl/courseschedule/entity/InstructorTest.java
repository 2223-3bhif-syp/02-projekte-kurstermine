package at.htl.courseschedule.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstructorTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_getters_after_simple_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "Franz",
                "Hintermüller",
                "+43 067 23423445",
                "progamerHD@gmail.com"
        );

        // act

        // assert
        assertThat(instructor.getId()).isNull();
        assertThat(instructor.getEmail()).isEqualTo("progamerHD@gmail.com");
        assertThat(instructor.getFirstName()).isEqualTo("Franz");
        assertThat(instructor.getLastName()).isEqualTo("Hintermüller");
        assertThat(instructor.getPhoneNr()).isEqualTo("+43 067 23423445");
    }

    @Test
    void test_getters_after_default_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor();

        // act

        // assert
        assertThat(instructor.getId()).isNull();
        assertThat(instructor.getEmail()).isNull();
        assertThat(instructor.getFirstName()).isNull();
        assertThat(instructor.getLastName()).isNull();
        assertThat(instructor.getPhoneNr()).isNull();
    }

    @Test
    void test_setters_after_default_constructors_ok() {
        // arrange
        Instructor instructor = new Instructor();

        // act
        instructor.setId(0L);
        instructor.setEmail("omegalul87@example.com");
        instructor.setFirstName("Mister");
        instructor.setLastName("Modern");
        instructor.setPhoneNr("+43 320 234234569");


        // assert
        assertThat(instructor.getId()).isEqualTo(0L);
        assertThat(instructor.getEmail()).isEqualTo("omegalul87@example.com");
        assertThat(instructor.getFirstName()).isEqualTo("Mister");
        assertThat(instructor.getLastName()).isEqualTo("Modern");
        assertThat(instructor.getPhoneNr()).isEqualTo("+43 320 234234569");
    }

    @Test
    void test_setters_with_simple_constructor_ok() {
        // arrange
        Instructor instructor = new Instructor(
                "Sirius",
                "Klein",
                "+43 234 23469876",
                "sirusk@gmail.com"
        );

        // act
        instructor.setId(2L);
        instructor.setEmail("thisismyemail@fastsafemail.com");
        instructor.setFirstName("Kai");
        instructor.setLastName("Komisch");
        instructor.setPhoneNr("+43 440 2346345");


        // assert
        assertThat(instructor.getId()).isEqualTo(2L);
        assertThat(instructor.getEmail()).isEqualTo("thisismyemail@fastsafemail.com");
        assertThat(instructor.getFirstName()).isEqualTo("Kai");
        assertThat(instructor.getLastName()).isEqualTo("Komisch");
        assertThat(instructor.getPhoneNr()).isEqualTo("+43 440 2346345");
    }
}