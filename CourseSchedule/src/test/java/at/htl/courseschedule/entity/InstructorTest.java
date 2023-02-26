package at.htl.courseschedule.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.db.api.Assertions.assertThat;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class InstructorTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testToString() {
        // arrange
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify

        // test
        assertEquals("Instructor{id=null, firstName='firstName', lastName='lastName', phoneNr='+43 6704070789', email='lastName@gmail.com'}", instructor.toString());
    }
}