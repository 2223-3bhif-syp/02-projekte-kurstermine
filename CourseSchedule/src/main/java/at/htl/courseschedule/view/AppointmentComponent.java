package at.htl.courseschedule.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AppointmentComponent extends AnchorPane {
    private String instructorFirstName;
    private String instructorLastName;
    private String courseName;

    public AppointmentComponent(String courseName, String instructorFirstName, String instructorLastName) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/appointment-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(AppointmentComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        // TODO
    }
}
