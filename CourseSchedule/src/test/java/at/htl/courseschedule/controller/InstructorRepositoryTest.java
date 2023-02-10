package at.htl.courseschedule.controller;

import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.db.output.Outputs.output;

class InstructorRepositoryTest {
    @Test
    void testInstructor() {
        Table table = new Table(Database.getDataSource(), "INSTRUCTOR");
        output(table).toConsole();
    }
}