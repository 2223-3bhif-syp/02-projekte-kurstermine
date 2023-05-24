package at.htl.courseschedule.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CourseScheduleController {
    @FXML
    private void initialize() {
        // TODO: init controller
    }


    @FXML
    private void onTestButtonClick(ActionEvent actionEvent) {
        (new Alert(Alert.AlertType.INFORMATION, "This is a test dialog", ButtonType.OK)).show();
    }
}
