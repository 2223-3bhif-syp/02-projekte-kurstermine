package at.htl.courseschedule.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1330, 600);
        stage.setScene(scene);
        stage.setTitle("Course Schedule");
        stage.show();
        fxmlLoader.<AdminViewController>getController().afterLoad(); // Manually invoke after load (workaround)
    }

    public static void main(String[] args) {
        launch();
    }
}
