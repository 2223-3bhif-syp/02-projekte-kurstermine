package at.htl.courseschedule.view;

import at.htl.courseschedule.entity.Appointment;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AppointmentComponent extends AnchorPane {
    private Appointment appointment;

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
        // TODO
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
