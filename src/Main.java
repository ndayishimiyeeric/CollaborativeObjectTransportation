import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane layout = new Pane();

        // Create a map with boundaries and obstacles
        Map map = new Map(300, 300);
        layout.getChildren().add(map.getObstacles());

        // Create an agent
        Agent agent = new Agent(100, 100);

        // Add the human-like figure to the layout
        layout.getChildren().add(agent.getFigure());

        // Position the figure
        agent.getFigure().setLayoutX(agent.getX());
        agent.getFigure().setLayoutY(agent.getY());

        // Create a timeline animation to make the agent move
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            // Check if the move is valid
            double newX = agent.getX() + 5;
            if (map.isValidMove(newX, agent.getY())) {
                // Update the agent's position
                agent.setX(newX);

                // Update the figure's position to match the agent's position
                agent.getFigure().setLayoutX(agent.getX());
                agent.getFigure().setLayoutY(agent.getY());
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MultiAgent System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
