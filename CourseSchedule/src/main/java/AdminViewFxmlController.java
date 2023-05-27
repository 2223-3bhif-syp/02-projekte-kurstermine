import at.htl.courseschedule.controller.AppointmentRepository;
import at.htl.courseschedule.controller.CourseRepository;
import at.htl.courseschedule.controller.InstructorRepository;
import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.entity.Course;
import at.htl.courseschedule.entity.Instructor;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

public class AdminViewFxmlController {
    @FXML
    private Button addButton;
    @FXML
    public void initialize() {
        addButton.setShape(new Circle(1.5));
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
        time.setPromptText("hh:mm:ss");
        ComboBox<Instructor> instructorSelector = new ComboBox<>();
        InstructorRepository instructorRepository = new InstructorRepository();
        instructorSelector.setItems(FXCollections.observableList(instructorRepository.findAll()));
        instructorSelector.setPromptText("Instructor");
        ComboBox<Course> courseSelector = new ComboBox<>();
        CourseRepository courseRepository = new CourseRepository();
        courseSelector.setItems(FXCollections.observableList(courseRepository.findAll()));
        courseSelector.setPromptText("Course");

        grid.add(new Label("Date:"), 0, 0);
        grid.add(date, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(time, 1, 1);
        grid.add(new Label("Instructor:"), 0, 2);
        grid.add(instructorSelector, 1, 2);
        grid.add(new Label("Course:"), 0, 3);
        grid.add(courseSelector, 1, 3);

        Node addButton = dialog.getDialogPane().lookupButton(addAppointmentButtonType);
        addButton.setDisable(true);

        BooleanBinding isAppointment = Bindings.createBooleanBinding(
                () -> {
                    if (date.getValue() == null || !date.getValue().isAfter(LocalDate.now())) {
                        // print error
                        return false;
                    }

                    if (!time.getText().matches("^\\d{2}:\\d{2}:\\d{2}$")) {
                        // print error
                        return false;
                    }

                    int[] timeValues = Arrays.stream(time.getText().split(":")).mapToInt(Integer::parseInt)
                            .toArray();

                    if (timeValues[0] > 20 || timeValues[0] < 8 ||
                            Arrays.stream(timeValues).skip(1).anyMatch(value -> value > 59) ||
                            Arrays.stream(timeValues).skip(1).anyMatch(value -> value < 0)) {
                        return false;
                    }

                    if (instructorSelector.getValue() == null) {
                        return false;
                    }

                    return courseSelector.getValue() != null;
                },
                date.valueProperty(), time.textProperty(), instructorSelector.valueProperty(),
                courseSelector.valueProperty()
        );

        isAppointment.addListener(e -> addButton.setDisable(!isAppointment.get()));
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (isAppointment.get()) {
                int[] timeValues = Arrays.stream(time.getText().split(":")).mapToInt(Integer::parseInt).toArray();
                LocalDateTime dateTime = LocalDateTime.of(date.getValue(), LocalTime.of(timeValues[0], timeValues[1],
                        timeValues[2]));
                return new Appointment(dateTime, instructorSelector.getValue(), courseSelector.getValue());
            }

            return null;
        });

        Optional<Appointment> optionalAppointment = dialog.showAndWait();

        if (optionalAppointment.isPresent()) {
            AppointmentRepository appointmentRepository = new AppointmentRepository();
            appointmentRepository.save(optionalAppointment.get());
        }
    }
}
