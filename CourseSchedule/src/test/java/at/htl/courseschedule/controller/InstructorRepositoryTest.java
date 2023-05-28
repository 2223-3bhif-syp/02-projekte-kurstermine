package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Instructor;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;

class InstructorRepositoryTest {
    private static final String tableName = "CS_INSTRUCTOR";

    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_save_save_simple_instructor_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.save(instructor);

        output(table).toConsole();

        instructor.setEmail("newEmail@gmail.com");
        insRep.save(instructor);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(instructor.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(instructor.getId())
                .value().isEqualTo(instructor.getFirstName())
                .value().isEqualTo(instructor.getLastName())
                .value().isEqualTo(instructor.getPhoneNr())
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void test_save_save_null_ok() {
        // arrange
        InstructorRepository instructorRepository = new InstructorRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> instructorRepository.save(null));
    }
    @Test
    void test_update_update_instructor_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.insert(instructor);

        output(table).toConsole();

        instructor.setEmail("newEmail@gmail.com");
        insRep.update(instructor);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(instructor.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(instructor.getId())
                .value().isEqualTo(instructor.getFirstName())
                .value().isEqualTo(instructor.getLastName())
                .value().isEqualTo(instructor.getPhoneNr())
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void test_update_update_null_ok() {
        // arrange
        InstructorRepository instructorRepository = new InstructorRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> instructorRepository.update(null));
    }

    @Test
    void test_insert_insert_instructor_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.insert(instructor);

        output(table).toConsole();

        // assert
        assertThat(instructor.getId()).isEqualTo(1);

        assertThat(table).row()
                .value().isEqualTo(instructor.getId())
                .value().isEqualTo(instructor.getFirstName())
                .value().isEqualTo(instructor.getLastName())
                .value().isEqualTo(instructor.getPhoneNr())
                .value().isEqualTo(instructor.getEmail());
    }

    @Test
    void test_insert_insert_null_ok() {
        // arrange
        InstructorRepository instructorRepository = new InstructorRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> instructorRepository.insert(null));
    }

    @Test
    void test_delete_delete_instructor_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor = new Instructor(
                "firstName",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.insert(instructor);
        insRep.delete(instructor);

        // assert
        assertThat(instructor.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_delete_delete_null_ok() {
        // arrange
        InstructorRepository instructorRepository = new InstructorRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> instructorRepository.delete(null));
    }

    @Test
    void test_findall_find_elements_ok() {
        // arrange
        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor1 = new Instructor(
                "1",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        Instructor instructor2 = new Instructor(
                "2",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        Instructor instructor3 = new Instructor(
                "3",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.save(instructor1);
        insRep.save(instructor2);
        insRep.save(instructor3);

        List<Instructor> instructorList = insRep.findAll();

        // assert
        assertThat(instructorList).hasSize(3)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(instructor1, instructor2, instructor3);
    }

    @Test
    void test_findbyid_find_elements_ok() {
        // arrange
        InstructorRepository insRep = new InstructorRepository();
        Instructor instructor1 = new Instructor(
                "1",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        Instructor instructor2 = new Instructor(
                "2",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );
        Instructor instructor3 = new Instructor(
                "3",
                "lastName",
                "+43 6704070789",
                "lastName@gmail.com"
        );

        // act
        insRep.save(instructor1);
        insRep.save(instructor2);
        insRep.save(instructor3);

        // assert
        assertThat(insRep.findById(instructor1.getId())).usingRecursiveComparison().isEqualTo(instructor1);
        assertThat(insRep.findById(instructor2.getId())).usingRecursiveComparison().isEqualTo(instructor2);
        assertThat(insRep.findById(instructor3.getId())).usingRecursiveComparison().isEqualTo(instructor3);
    }

    @Test
    void test_findbyid_find_invalid_id() {
        // arrange
        InstructorRepository insRep = new InstructorRepository();

        // act

        // assert
        assertThat(insRep.findById(1)).isNull();
    }
}