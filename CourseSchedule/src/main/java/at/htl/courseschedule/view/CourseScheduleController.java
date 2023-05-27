package at.htl.courseschedule.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CourseScheduleController {
    private static final int DAYS_PER_WEEK = 7;
    private static final int FIRST_HOUR = 8;
    private static final int LAST_HOUR = 20;
    private static final int DISPLAYED_HOURS = LAST_HOUR - FIRST_HOUR + 1; // +1: typical fencepost problem

    @FXML
    private GridPane timeGrid;

    @FXML
    private void initialize() {
        // Add rows and cols with correct height and width
        addGridConstraints();
        addWeekdayLabels();
        addHourLabels();
    }

    private void addGridConstraints() {
        ObservableList<RowConstraints> rowConstraints = timeGrid.getRowConstraints();
        ObservableList<ColumnConstraints> colConstraints = timeGrid.getColumnConstraints();

        // first column is smaller since it only contains the time
        ColumnConstraints timeCol = new ColumnConstraints();
        timeCol.setPercentWidth(5.5d);
        colConstraints.add(timeCol);

        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            ColumnConstraints dayCol = new ColumnConstraints();
            dayCol.setPercentWidth(13.5d);
            colConstraints.add(dayCol);
        }

        // first column is smaller since it only contains the time
        RowConstraints labelRow = new RowConstraints();
        labelRow.setPercentHeight(9.0d);
        rowConstraints.add(labelRow);

        for (int i = 0; i < DISPLAYED_HOURS; i++) {
            RowConstraints hourRow = new RowConstraints();
            hourRow.setPercentHeight(13.0d);
            rowConstraints.add(hourRow);
        }
    }

    private void addWeekdayLabels() {
        // Prepare Dates of the current week
        LocalDate now = LocalDate.now();
        List<LocalDate> weekDates = Arrays.stream(DayOfWeek.values()).map(now::with).toList();

        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            // Convert date back to day of week and get short form for a german locale
            Label weekday = new Label(weekDates.get(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.GERMAN));
            weekday.getStyleClass().add("bg-lighter");
            Label date = new Label(weekDates.get(i).format(DateTimeFormatter.ofPattern("dd.MM")));
            date.getStyleClass().add("bg-lighter");
            date.setDisable(true);

            HBox labelBox = new HBox(weekday, date);
            labelBox.getStyleClass().add("bg-lighter");
            labelBox.setAlignment(Pos.CENTER);
            labelBox.setSpacing(2);

            timeGrid.add(labelBox, i + 1, 0); // first column is empty => i+1
        }
    }

    private void addHourLabels() {
        for (int i = FIRST_HOUR; i <= LAST_HOUR; i++) {
            Label hour = new Label(String.format("%02d:00", i));
            hour.getStyleClass().add("bg-lighter");
            hour.setDisable(true);

            HBox labelBox = new HBox(hour);
            labelBox.getStyleClass().add("bg-lighter");
            labelBox.setAlignment(Pos.CENTER);

            timeGrid.add(labelBox, 0, i - FIRST_HOUR + 1);
        }
    }
}
