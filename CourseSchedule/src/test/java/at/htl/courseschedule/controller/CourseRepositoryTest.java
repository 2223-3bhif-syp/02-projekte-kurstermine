package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;
import at.htl.courseschedule.entity.Course;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;

class CourseRepositoryTest {
    private static final String tableName = "CS_COURSE";

    @BeforeEach
    public void setUp() {
        // to make sure every Table is empty and set up right
        SqlRunner.dropTablesAndCreateEmptyTables();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void test_save_save_simple_course_and_db_check_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.save(course);

        output(table).toConsole();

        course.setMinutesPerAppointment(60);
        courseRepository.save(course);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(course.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(course.getId())
                .value().isEqualTo(course.getName())
                .value().isEqualTo(course.getDescription())
                .value().isEqualTo(course.getMinutesPerAppointment())
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void test_save_save_null_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> courseRepository.save(null));
    }

    @Test
    void test_update_update_course_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.insert(course);

        output(table).toConsole();

        course.setMinutesPerAppointment(60);
        courseRepository.update(course);

        // assert
        table = new Table(
                Database.getDataSource(),
                tableName
        );

        output(table).toConsole();

        assertThat(course.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(course.getId())
                .value().isEqualTo(course.getName())
                .value().isEqualTo(course.getDescription())
                .value().isEqualTo(course.getMinutesPerAppointment())
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void test_update_update_null_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> courseRepository.update(null));
    }

    @Test
    void test_insert_insert_course_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.insert(course);

        output(table).toConsole();

        // assert
        assertThat(course.getId()).isEqualTo(1);

        assertThat(table).row(0)
                .value().isEqualTo(course.getId())
                .value().isEqualTo(course.getName())
                .value().isEqualTo(course.getDescription())
                .value().isEqualTo(course.getMinutesPerAppointment())
                .value().isEqualTo(course.getAmountOfAppointments());
    }

    @Test
    void test_insert_insert_null_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> courseRepository.insert(null));
    }

    @Test
    void test_delete_delete_inserted_course_ok() {
        // arrange
        Table table = new Table(
                Database.getDataSource(),
                tableName
        );

        CourseRepository courseRepository = new CourseRepository();
        Course course = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.insert(course);
        courseRepository.delete(course);

        // assert
        assertThat(course.getId()).isNull();

        assertThat(table).hasNumberOfRows(0);
    }

    @Test
    void test_delete_delete_null_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();

        // act

        // assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> courseRepository.delete(null));
    }

    @Test
    void test_findall_retreive_inserted_data_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Course course2 = new Course(
                "Course2",
                "Test Course",
                90,
                1
        );
        Course course3 = new Course(
                "Course3",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        List<Course> courseList = courseRepository.findAll();

        // assert
        assertThat(courseList).hasSize(3)
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(course1, course2, course3);
    }

    @Test
    void test_findbyid_find_inserted_elements_by_id_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();
        Course course1 = new Course(
                "Course1",
                "Test Course",
                90,
                1
        );
        Course course2 = new Course(
                "Course2",
                "Test Course",
                90,
                1
        );
        Course course3 = new Course(
                "Course3",
                "Test Course",
                90,
                1
        );

        // act
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        // assert
        assertThat(courseRepository.findById(course1.getId())).usingRecursiveComparison().isEqualTo(course1);
        assertThat(courseRepository.findById(course2.getId())).usingRecursiveComparison().isEqualTo(course2);
        assertThat(courseRepository.findById(course3.getId())).usingRecursiveComparison().isEqualTo(course3);
    }

    @Test
    void test_findbyid_search_for_element_not_in_table_ok() {
        // arrange
        CourseRepository courseRepository = new CourseRepository();

        // act

        // assert
        assertThat(courseRepository.findById(1)).isNull();
    }
}