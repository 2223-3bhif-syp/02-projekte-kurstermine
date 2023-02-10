package at.htl.courseschedule.controller;

import at.htl.courseschedule.database.SqlRunner;

public class Main {
    public static void main(String[] args) {
        SqlRunner.dropAndCreateTablesWithExampleData();
    }
}
