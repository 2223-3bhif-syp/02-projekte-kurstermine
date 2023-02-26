package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Instructor;
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

class InstructorRepositoryTest {
    private static String tableName = "CS_INSTRUCTOR";
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

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.save(instructor);

        instructor.setEmail("newEmail@gmail.com");
        insRep.save(instructor);

        // test
        assertEquals(instructor.getId(), 1);

        assertThat(table).column("I_ID")
                .value().isEqualTo(instructor.getId());
        assertThat(table).column("I_FIRST_NAME")
                .value().isEqualTo(instructor.getFirstName());
        assertThat(table).column("I_LAST_NAME")
                .value().isEqualTo(instructor.getLastName());
        assertThat(table).column("I_PHONE_NR")
                .value().isEqualTo(instructor.getPhoneNr());
        assertThat(table).column("I_EMAIL")
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void update() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.insert(instructor);

        instructor.setEmail("newEmail@gmail.com");
        insRep.update(instructor);

        // test
        assertEquals(instructor.getId(), 1);

        assertThat(table).column("I_ID")
                .value().isEqualTo(instructor.getId());
        assertThat(table).column("I_FIRST_NAME")
                .value().isEqualTo(instructor.getFirstName());
        assertThat(table).column("I_LAST_NAME")
                .value().isEqualTo(instructor.getLastName());
        assertThat(table).column("I_PHONE_NR")
                .value().isEqualTo(instructor.getPhoneNr());
        assertThat(table).column("I_EMAIL")
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void insert() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.insert(instructor);

        // test
        assertEquals(instructor.getId(), 1);

        assertThat(table).column("I_ID")
                .value().isEqualTo(instructor.getId());
        assertThat(table).column("I_FIRST_NAME")
                .value().isEqualTo(instructor.getFirstName());
        assertThat(table).column("I_LAST_NAME")
                .value().isEqualTo(instructor.getLastName());
        assertThat(table).column("I_PHONE_NR")
                .value().isEqualTo(instructor.getPhoneNr());
        assertThat(table).column("I_EMAIL")
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void delete() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor("firstName", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.insert(instructor);
        insRep.delete(instructor);

        // test
        assertEquals(null, instructor.getId());

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void findAll() {
        // arrange
        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor1 = new Instructor("1", "lastName", "+43 6704070789", "lastName@gmail.com");
        Instructor instructor2 = new Instructor("2", "lastName", "+43 6704070789", "lastName@gmail.com");
        Instructor instructor3 = new Instructor("3", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.save(instructor1);
        insRep.save(instructor2);
        insRep.save(instructor3);

        List<Instructor> instructorList = insRep.findAll();

        // test
        assertEquals(3, instructorList.size());

        assertTrue(instructorList.stream().anyMatch(instructor -> instructor1.toString().equals(instructor.toString())));
        assertTrue(instructorList.stream().anyMatch(instructor -> instructor2.toString().equals(instructor.toString())));
        assertTrue(instructorList.stream().anyMatch(instructor -> instructor3.toString().equals(instructor.toString())));
    }

    @Test
    void findById() {
        // arrange
        Table table = new Table(Database.getDataSource(), tableName);

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor1 = new Instructor("1", "lastName", "+43 6704070789", "lastName@gmail.com");
        Instructor instructor2 = new Instructor("2", "lastName", "+43 6704070789", "lastName@gmail.com");
        Instructor instructor3 = new Instructor("3", "lastName", "+43 6704070789", "lastName@gmail.com");

        // modify
        insRep.save(instructor1);
        insRep.save(instructor2);
        insRep.save(instructor3);

        // test
        assertEquals(instructor1.toString(), insRep.findById(instructor1.getId()).toString());
        assertEquals(instructor2.toString(), insRep.findById(instructor2.getId()).toString());
        assertEquals(instructor3.toString(), insRep.findById(instructor3.getId()).toString());
    }
}