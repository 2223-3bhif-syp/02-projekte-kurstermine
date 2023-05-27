package at.htl.courseschedule.view;

import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CourseScheduleController {
    private static final int DAYS_PER_WEEK = 7;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int FIRST_HOUR = 8;
    private static final int LAST_HOUR = 20;
    private static final int DISPLAYED_HOURS = LAST_HOUR - FIRST_HOUR + 1; // +1: typical fencepost problem
    private static final double DAY_OF_WEEK_ROW_PERCENTAGE = 5.0d;
    private static final double TIME_ROW_PERCENTAGE = (100 - DAY_OF_WEEK_ROW_PERCENTAGE) / DISPLAYED_HOURS;
    private static final double TIME_COL_PERCENTAGE = 5.5d;
    private static final double DAY_OF_WEEK_COL_PERCENTAGE = 13.5d;

    @FXML
    private GridPane timeGrid;

    private AnchorPane drawPane;

    @FXML
    private void initialize() {
        // Add rows and cols with correct height and width
        addGridConstraints();
        addWeekdayLabels();
        addHourLabels();
        drawPane = initDrawPane();
    }

    public void afterLoad() {
        // Show some sample data
        Instructor instructor = new Instructor("k", "k", ",", " ");
        Course course1 = new Course("a", "b", 60, 10);
        Course course2 = new Course("a", "b", 30, 10);

        Appointment app = new Appointment(
                LocalDateTime.of(2023, 05, 22, 8, 30, 0),
                instructor,
                course1);
        Appointment app2 = new Appointment(
                LocalDateTime.of(2023, 05, 22, 9, 30, 0),
                instructor,
                course2);

        showAppointment(app);
        showAppointment(app2);
    }

    private void addGridConstraints() {
        ObservableList<RowConstraints> rowConstraints = timeGrid.getRowConstraints();
        ObservableList<ColumnConstraints> colConstraints = timeGrid.getColumnConstraints();

        // first column is smaller since it only contains the time
        ColumnConstraints timeCol = new ColumnConstraints();
        timeCol.setPercentWidth(TIME_COL_PERCENTAGE);
        colConstraints.add(timeCol);

        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            ColumnConstraints dayCol = new ColumnConstraints();
            dayCol.setPercentWidth(DAY_OF_WEEK_COL_PERCENTAGE);
            colConstraints.add(dayCol);
        }

        // first column is smaller since it only contains the time
        RowConstraints labelRow = new RowConstraints();
        labelRow.setPercentHeight(DAY_OF_WEEK_ROW_PERCENTAGE);
        rowConstraints.add(labelRow);

        for (int i = 0; i < DISPLAYED_HOURS; i++) {
            RowConstraints hourRow = new RowConstraints();
            hourRow.setPercentHeight(TIME_ROW_PERCENTAGE);
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

    private AnchorPane initDrawPane() {
        AnchorPane pane = new AnchorPane();

        GridPane.setColumnSpan(pane, DAYS_PER_WEEK);
        GridPane.setRowSpan(pane, DISPLAYED_HOURS);

        timeGrid.add(pane, 1, 1);
        return pane;
    }

    private double getPosXFromDayOfWeek(DayOfWeek dayOfWeek) {
        return (dayOfWeek.getValue() - 1) * (DAY_OF_WEEK_COL_PERCENTAGE / 100 * timeGrid.getWidth());
    }

    private double getPosYFromStartTime(LocalDateTime start) {
        return (drawPane.getHeight() / (DISPLAYED_HOURS * MINUTES_PER_HOUR)) * ((start.getHour() - FIRST_HOUR) * 60 + start.getMinute());
    }

    private double getHeight(int minutesInAppointment) {
        return (drawPane.getHeight() / (DISPLAYED_HOURS * MINUTES_PER_HOUR)) * minutesInAppointment - timeGrid.getHgap();
    }

    private double getWidth() {
        return DAY_OF_WEEK_COL_PERCENTAGE / 100 * timeGrid.getWidth() - timeGrid.getHgap();
    }

    private void showAppointment(Appointment appointment) {
        AppointmentComponent appointmentComponent = new AppointmentComponent(appointment.getCourse().getName(),
                appointment.getInstructor().getFirstName(),
                appointment.getInstructor().getLastName());

        AnchorPane.setLeftAnchor(appointmentComponent, getPosXFromDayOfWeek(appointment.getStart().getDayOfWeek()));
        AnchorPane.setTopAnchor(appointmentComponent, getPosYFromStartTime(appointment.getStart()));

        appointmentComponent.setPrefHeight(getHeight(appointment.getCourse().getMinutesPerAppointment()));
        appointmentComponent.setPrefWidth(getWidth());

        drawPane.getChildren().add(appointmentComponent);
    }

    public void addButtonClicked(ActionEvent actionEvent) {

    }
}
