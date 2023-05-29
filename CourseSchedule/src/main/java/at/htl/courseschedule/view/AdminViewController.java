package at.htl.courseschedule.view;

import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import at.htl.courseschedule.service.AppointmentService;
import at.htl.courseschedule.service.CourseService;
import at.htl.courseschedule.service.InstructorService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AdminViewController {
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
    private Button weekBeforeBtn;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button weekAfterBtn;

    @FXML
    private GridPane timeGrid;

    @FXML
    private Button addButton;

    private Pane drawPane;
    private AppointmentService appointmentService;
    private InstructorService instructorService;
    private CourseService courseService;
    private FilteredList<Appointment> filteredAppointments;

    @FXML
    public void initialize() {
        //Pagination initialisation
        initPagination();

        //add Btn initialisation
        addButton.setShape(new Circle(1.5));

        // Add rows and cols with correct height and width
        addGridConstraints();
        addWeekdayLabels();
        addHourLabels();
        drawPane = initDrawPane();

        appointmentService = AppointmentService.getInstance();
        instructorService = InstructorService.getInstance();
        courseService = CourseService.getInstance();

        drawPane.widthProperty().addListener((observableValue, number, t1) -> recalcComponentSizes());
        drawPane.heightProperty().addListener((observableValue, number, t1) -> recalcComponentSizes());

        filteredAppointments = new FilteredList<>(appointmentService.getAppointments());
        filteredAppointments.addListener((ListChangeListener<Appointment>) change -> redrawAppointments());
        filteredAppointments.setPredicate((a) ->
                a.getStart()
                        .isAfter(getDateOfDayOfWeek(DayOfWeek.MONDAY)
                                .atStartOfDay())
                &&
                a.getStart()
                        .plusMinutes(a.getCourse()
                                .getMinutesPerAppointment())
                        .isBefore(getDateOfDayOfWeek(DayOfWeek.SUNDAY)
                                .atStartOfDay()
                                .plusHours(23))
        );

        // Force initial draw
        redrawAppointments();
    }

    private void initPagination(){
        Image btnImage = new Image(String.valueOf(App.class.getResource("/arrow-image.png")));

        ImageView viewLeft = new ImageView(btnImage);
        ImageView viewRight = new ImageView(btnImage);

        viewLeft.setRotate(180);
        viewLeft.setPreserveRatio(true);
        viewRight.setPreserveRatio(true);
        viewLeft.setFitHeight(17);
        viewRight.setFitHeight(17);

        weekBeforeBtn.setGraphic(viewLeft);
        weekAfterBtn.setGraphic(viewRight);

        datepicker.setValue(LocalDate.now());
        datepicker.setOnAction(event -> {
            //resets the dates from above
            addWeekdayLabels();

            //sets the predicate for the current appointments and changes them via the change listener
            filteredAppointments.setPredicate((a) ->
                    a.getStart()
                            .isAfter(getDateOfDayOfWeek(DayOfWeek.MONDAY)
                                    .atStartOfDay())
                    &&
                    a.getStart()
                            .plusMinutes(a.getCourse()
                                    .getMinutesPerAppointment())
                            .isBefore(getDateOfDayOfWeek(DayOfWeek.SUNDAY)
                                    .atStartOfDay()
                                    .plusHours(23))
            );
        });
    }

    private LocalDate getDateOfDayOfWeek(DayOfWeek dayOfWeek) {
        List<LocalDate> weekDates = Arrays.stream(DayOfWeek.values())
                .map(datepicker.getValue()::with)
                .toList();
        return weekDates.get(dayOfWeekToInt(dayOfWeek));
    }

    private int dayOfWeekToInt(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case TUESDAY -> 1;
            case WEDNESDAY -> 2;
            case THURSDAY -> 3;
            case FRIDAY -> 4;
            case SATURDAY -> 5;
            case SUNDAY -> 6;
            default -> 0;
        };
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
        List<LocalDate> weekDates = Arrays.stream(DayOfWeek.values()).map(datepicker.getValue()::with).toList();

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

    private Pane initDrawPane() {
        Pane pane = new Pane();

        GridPane.setColumnSpan(pane, DAYS_PER_WEEK);
        GridPane.setRowSpan(pane, DISPLAYED_HOURS);

        timeGrid.add(pane, 1, 1);
        return pane;
    }

    private double getPosXFromDayOfWeek(DayOfWeek dayOfWeek) {
        return ((drawPane.getWidth() - 6 * timeGrid.getHgap()) / DAYS_PER_WEEK + timeGrid.getHgap())
                * (dayOfWeek.getValue() - 1);
    }


    private double getPosYFromStartTime(LocalDateTime start) {
        return ((drawPane.getHeight() * ((start.getHour() - FIRST_HOUR) * 60 + start.getMinute()))
                / (DISPLAYED_HOURS * MINUTES_PER_HOUR));
    }

    private double getHeight(int minutesInAppointment) {
        return ((drawPane.getHeight() * minutesInAppointment) / (DISPLAYED_HOURS * MINUTES_PER_HOUR));
    }

    private double getWidth() {
        return (drawPane.getWidth() - 6 * timeGrid.getHgap()) / DAYS_PER_WEEK;
    }

    private void setCalculatedComponentSize(AppointmentComponent appointmentComponent) {
        double posX = getPosXFromDayOfWeek(appointmentComponent
                .getAppointment()
                .getStart()
                .getDayOfWeek());
        double posY = getPosYFromStartTime(appointmentComponent
                .getAppointment()
                .getStart());
        double height = getHeight(appointmentComponent
                .getAppointment()
                .getCourse()
                .getMinutesPerAppointment());
        double width = getWidth();

        appointmentComponent.setPrefHeight(height);
        appointmentComponent.setPrefWidth(width);
        appointmentComponent.setMaxWidth(width);
        appointmentComponent.setMaxHeight(height);

        Pane.layoutInArea(appointmentComponent,
                posX,
                posY,
                width,
                height,
                0,
                null,
                true,
                true,
                HPos.CENTER,
                VPos.CENTER,
                true);
    }

    private void showAppointment(Appointment appointment) {
        // TODO: Properly handle this edge case
        if (appointment.getStart().getHour() > LAST_HOUR || appointment.getStart().getHour() < FIRST_HOUR
            || appointment.getStart().plusMinutes(appointment
                .getCourse()
                .getMinutesPerAppointment()
                ).getHour() > LAST_HOUR) {
            System.err.println("Refusing to draw appointment: " + appointment.toString());
            return;
        }

        AppointmentComponent appointmentComponent = new AppointmentComponent(appointment);

        setCalculatedComponentSize(appointmentComponent);

        drawPane.getChildren().add(appointmentComponent);
    }

    private void recalcComponentSizes() {
        for (Node ac: drawPane.getChildren()) {
            if (ac instanceof AppointmentComponent) {
                setCalculatedComponentSize((AppointmentComponent) ac);
            }
        }
    }

    private void redrawAppointments() {
        drawPane.getChildren().clear();
        filteredAppointments.forEach(this::showAppointment);
    }

    @FXML
    private void addAppointment() {
        Dialog<Appointment> dialog = new Dialog<>();
        dialog.setTitle("Add Appointment");
        ButtonType addAppointmentButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addAppointmentButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker date = new DatePicker();
        date.setPromptText("Date");
        TextField time = new TextField();
        time.setPromptText("hh:mm");
        ComboBox<Instructor> instructorSelector = new ComboBox<>();
        instructorSelector.setItems(instructorService.getInstructors());
        instructorSelector.setPromptText("Instructor");
        ComboBox<Course> courseSelector = new ComboBox<>();
        courseSelector.setItems(courseService.getCourses());
        courseSelector.setPromptText("Course");
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        grid.add(errorLabel, 0, 0, 2, 1);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(date, 1, 1);
        grid.add(new Label("Time:"), 0, 2);
        grid.add(time, 1, 2);
        grid.add(new Label("Instructor:"), 0, 3);
        grid.add(instructorSelector, 1, 3);
        grid.add(new Label("Course:"), 0, 4);
        grid.add(courseSelector, 1, 4);

        Node addButton = dialog.getDialogPane().lookupButton(addAppointmentButtonType);
        addButton.setDisable(true);

        BooleanBinding isAppointment = Bindings.createBooleanBinding(
                () -> {
                    if (date.getValue() == null || !date.getValue().isAfter(LocalDate.now())) {
                        errorLabel.setText("The date has to be after today!");
                        return false;
                    }

                    LocalTime localTime;

                    try {
                        localTime = LocalTime.parse(time.getText());
                    } catch (DateTimeParseException e) {
                        errorLabel.setText("Enter a valid time format hh:mm!");
                        return false;
                    }

                    if (localTime.getHour() > 20 || localTime.getHour() < 8) {
                        errorLabel.setText("The time you entered is not valid!");
                        return false;
                    }

                    if (instructorSelector.getValue() == null) {
                        errorLabel.setText("You have to select an instructor!");
                        return false;
                    }

                    if (courseSelector.getValue() == null) {
                        errorLabel.setText("You have to select a course!");
                        return false;
                    }

                    errorLabel.setText("");
                    return true;
                },
                date.valueProperty(), time.textProperty(), instructorSelector.valueProperty(),
                courseSelector.valueProperty()
        );

        isAppointment.addListener(e -> addButton.setDisable(!isAppointment.get()));
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (isAppointment.get()) {
                LocalDateTime dateTime = LocalDateTime.of(date.getValue(), LocalTime.parse(time.getText()));
                return new Appointment(dateTime, instructorSelector.getValue(), courseSelector.getValue());
            }

            return null;
        });

        Optional<Appointment> optionalAppointment = dialog.showAndWait();
        optionalAppointment.ifPresent(appointment -> appointmentService.add(appointment));
    }

    @FXML
    private void weekAfterBtnClicked(ActionEvent actionEvent) {
        datepicker.setValue(datepicker.getValue().plusWeeks(1));
        datepicker.setValue(getDateOfDayOfWeek(DayOfWeek.MONDAY));
    }

    @FXML
    private void weekBeforeBtnClicked(ActionEvent actionEvent) {
        datepicker.setValue(datepicker.getValue().minusWeeks(1));
        datepicker.setValue(getDateOfDayOfWeek(DayOfWeek.MONDAY));
    }
}
