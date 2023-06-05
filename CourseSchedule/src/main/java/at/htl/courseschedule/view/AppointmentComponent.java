package at.htl.courseschedule.view;

import at.htl.courseschedule.entity.Appointment;
import at.htl.courseschedule.service.AppointmentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

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

        HBox hBox = new HBox(grid);
        addDeleteBtn(hBox);

        dialog.getDialogPane().setContent(hBox);
        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.CLOSE
        );

        dialog.show();
    }

    private void addDeleteBtn(HBox hBox){
        Button btn = new Button();
        btn.setText("DELETE");
        btn.setStyle("-fx-background-color: rgba(255,0,17,0.32); -fx-font-weight: bold");

        btn.setOnAction(actionEvent -> {
            AppointmentService.getInstance().remove(this.appointment);

            if (!AppointmentService.getInstance().getAppointments().contains(appointment)) {
                btn.setDisable(true);
            }
        });

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        hBox.getChildren().add(region);
        hBox.getChildren().add(btn);
    }
}
