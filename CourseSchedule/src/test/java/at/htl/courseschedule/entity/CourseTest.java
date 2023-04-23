package at.htl.courseschedule.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_ToString_AfterNormalCtor_ShouldResultInEnteredValuesAndIdNull() {
        // arrange
        Course course = new Course("Course1", "Test Course", 90,
                1);

        // act

        // assert
        assertThat(course.toString())
                .isEqualTo("Course{id=null, name='Course1', description='Test Course', minutesPerAppointment=90, amountOfAppointments=1}");
    }

    @Test
    void test_Getters_AfterNormalCtor_ShouldResultInEnteredValuesAndIdNull() {
        // arrange
        Course course = new Course("Course1", "Test Course", 90,
                1);

        // act

        // assert
        assertThat(course.getId()).isNull();
        assertThat(course.getName()).isEqualTo("Course1");
        assertThat(course.getDescription()).isEqualTo("Test Course");
        assertThat(course.getMinutesPerAppointment()).isEqualTo(90);
        assertThat(course.getAmountOfAppointments()).isEqualTo(1);
    }

    @Test
    void test_Getters_AfterDefaultCtor_ShouldResultInDefaultValues() {
        // arrange
        Course course = new Course();

        // act

        // assert
        assertThat(course.getId()).isNull();
        assertThat(course.getName()).isNull();
        assertThat(course.getDescription()).isNull();
        assertThat(course.getAmountOfAppointments()).isEqualTo(0);
        assertThat(course.getMinutesPerAppointment()).isEqualTo(0);
    }

    @Test
    void test_Setters_SimpleChangesOfDefaultValues_ShouldResultInNewValues() {
        // arrange
        Course course = new Course();

        // act
        course.setId(0L);
        course.setName("Hello");
        course.setDescription("This course is called \"Hello\"");
        course.setMinutesPerAppointment(60);
        course.setAmountOfAppointments(15);

        // assert
        assertThat(course.getId()).isEqualTo(0L);
        assertThat(course.getName()).isEqualTo("Hello");
        assertThat(course.getDescription()).isEqualTo("This course is called \"Hello\"");
        assertThat(course.getMinutesPerAppointment()).isEqualTo(60);
        assertThat(course.getAmountOfAppointments()).isEqualTo(15);
    }

    @Test
    void test_Setters_SimpleChangesOfCtorSetValues_ShouldResultInNewValues() {
        // arrange
        Course course = new Course("Das große ABC", "In diesem Kurs lernt niemand was?",
                0, 0);

        // act
        course.setId(120L);
        course.setName("Das kleine ABC");
        course.setDescription("ABCDEFGHIJKLMNOP");
        course.setMinutesPerAppointment(75);
        course.setAmountOfAppointments(18);

        // assert
        assertThat(course.getId()).isEqualTo(120L);
        assertThat(course.getName()).isEqualTo("Das kleine ABC");
        assertThat(course.getDescription()).isEqualTo("ABCDEFGHIJKLMNOP");
        assertThat(course.getMinutesPerAppointment()).isEqualTo(75);
        assertThat(course.getAmountOfAppointments()).isEqualTo(18);
    }
}