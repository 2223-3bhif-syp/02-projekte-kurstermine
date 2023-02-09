package at.htl.kurstermine.controller;

import at.htl.kurstermine.database.SqlRunner;
import at.htl.kurstermine.database.SqlScript;

public class Main {
    public static void main(String[] args) {
        SqlRunner.runScript(SqlScript.CREATE);
    }
}
