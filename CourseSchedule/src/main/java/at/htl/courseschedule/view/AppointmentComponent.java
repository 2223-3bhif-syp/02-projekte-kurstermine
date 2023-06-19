package at.htl.courseschedule.view;

import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import at.htl.courseschedule.service.AppointmentService;
import at.htl.courseschedule.service.CourseService;
import at.htl.courseschedule.service.InstructorService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AppointmentComponent extends AnchorPane {
    private Appointment appointment;

    @FXML
    private Label courseNameLabel;

    public AppointmentComponent(Appointment appointment) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/appointment-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(AppointmentComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.appointment = appointment;

        courseNameLabel.setText(appointment.getCourse().getName());

        this.setOnMouseClicked(e -> openAppointmentDialog());
    }

    public Appointment getAppointment() {
        return appointment;
    }

    private void openAppointmentDialog() {
        Dialog<Appointment> dialog = new Dialog<>();

        dialog.setTitle("Appointment Information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Course:"), 0, 0);
        grid.add(new Label(appointment.getCourse().getName()), 1, 0);
        grid.add(new Label("Instructor:"), 0, 1);
        grid.add(new Label(appointment.getInstructor().toString()), 1, 1);
        grid.add(new Label("Date"), 0, 2);
        grid.add(new Label(appointment.getStart().toLocalDate().toString()), 1, 2);
        grid.add(new Label("Time:"), 0, 3);
        grid.add(new Label(appointment.getStart().toLocalTime().toString()), 1, 3);
        grid.add(new Label("Duration:"), 0, 4);
        grid.add(new Label(String.format("%d m", appointment.getCourse().getMinutesPerAppointment())), 1, 4);

        VBox vBox = new VBox(grid);

        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.NO,
                ButtonType.CANCEL,
                ButtonType.YES
        );

        addDeleteButton(dialog);
        addEditButton(dialog);

        dialog.show();
    }

    private void openEditAppointmentDialog() {
        Dialog<Appointment> dialog = new Dialog<>();

        dialog.setTitle("Edit Appointment");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Course:"), 0, 0);
        ComboBox<Course> courseSelector = new ComboBox<>();
        courseSelector.setItems(CourseService.getInstance().getCourses());
        courseSelector.setPromptText("Course");
        courseSelector.setValue(appointment.getCourse());
        grid.add(courseSelector, 1, 0);
        grid.add(new Label("Instructor:"), 0, 1);
        ComboBox<Instructor> instructorSelector = new ComboBox<>();
        instructorSelector.setItems(InstructorService.getInstance().getInstructors());
        instructorSelector.setValue(appointment.getInstructor());
        grid.add(instructorSelector, 1, 1);
        grid.add(new Label("Date"), 0, 2);
        DatePicker date = new DatePicker(appointment.getStart().toLocalDate());
        grid.add(date, 1, 2);
        grid.add(new Label("Time:"), 0, 3);
        TextField time = new TextField(appointment.getStart().toLocalTime().toString());
        grid.add(time, 1, 3);
        grid.add(new Label("Duration:"), 0, 4);
        TextField duration = new TextField(String.format("%d m", appointment.getCourse().getMinutesPerAppointment()));
        duration.setEditable(false);
        grid.add(duration, 1, 4);

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

        VBox vBox = new VBox(errorLabel, grid);

        dialog.getDialogPane().setContent(vBox);

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.NO
        );

        DialogPane pane = dialog.getDialogPane();
        Button saveBtn = (Button) pane.lookupButton(ButtonType.OK);
        saveBtn.setText("SAVE");
        Button cancelBtn = (Button) pane.lookupButton(ButtonType.NO);
        cancelBtn.setText("CANCEL");

        cancelBtn.setOnAction(e -> {
            dialog.close();
        });

        saveBtn.disableProperty().bind(isAppointment.not());

        saveBtn.setOnAction(e -> {
            appointment.setStart(LocalDateTime.of(date.getValue(), LocalTime.parse(time.getText())));
            appointment.setInstructor(instructorSelector.getValue());
            appointment.setCourse(courseSelector.getValue());
            AppointmentService.getInstance().update(appointment);
        });


        dialog.show();
    }

    private void addEditButton(Dialog<Appointment> dialog) {
        DialogPane pane = dialog.getDialogPane();
        Button editButton = (Button) pane.lookupButton(ButtonType.YES);

        editButton.setText("EDIT");
        editButton.setOnAction(e -> {
            openEditAppointmentDialog();
        });
    }

    private void addDeleteButton(Dialog<Appointment> dialog) {
        DialogPane pane = dialog.getDialogPane();
        Button deleteButton = (Button) pane.lookupButton(ButtonType.NO);
        String idle = "-fx-background-color: rgba(255,0,17,0.32); -fx-font-weight: bold";
        String hover = "-fx-background-color:  rgba(255,0,17,0.64); -fx-font-weight: bold";

        deleteButton.setText("DELETE");
        deleteButton.setStyle(idle);
        deleteButton.setOnMouseEntered(e -> {
            deleteButton.setStyle(hover);
        });
        deleteButton.setOnMouseExited(e -> {
            deleteButton.setStyle(idle);
        });
        deleteButton.setOnAction(e -> {
            System.out.println(this.appointment.toString());
            AppointmentService.getInstance().remove(this.appointment);

            if (!AppointmentService.getInstance().getAppointments().contains(appointment)) {
                dialog.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not delete appointment");
                alert.setContentText("The appointment could not be deleted. Please try again.");
                alert.showAndWait();
            }
        });
    }
}
