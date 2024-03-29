package at.htl.courseschedule.view;

import at.htl.courseschedule.database.SqlRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        SqlRunner.dropAndCreateTablesWithExampleData();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1330, 600);
        stage.setScene(scene);
        stage.setTitle("Course Schedule");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
