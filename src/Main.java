import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane layout = new StackPane();

        Button button = new Button("Get java version");

        button.setOnAction(actionEvent -> {
            System.out.println(System.getProperty("java.version"));
        });

        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MultiAgent System");
        primaryStage.show();
    }

    public static void main(String[] args) {launch();}
}