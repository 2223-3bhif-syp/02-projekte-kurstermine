package at.htl.courseschedule.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.db.api.Assertions.assertThat;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class CourseTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testToString() {
        // arrange
        Course course = new Course("Course1", "Test Course", 90, 1);

        // modify

        // test
        assertEquals("Course{id=null, name='Course1', description='Test Course', minutesPerAppointment=90, amountOfAppointments=1}", course.toString());
    }
}