import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.stream.Collectors;

public class Map {
    private Group obstacles;
    private double width;
    private double height;

    public Map(double width, double height) {
        this.width = width;
        this.height = height;
        createObstacles();
    }

    private void createObstacles() {
        Rectangle obstacle1 = new Rectangle(150, 0, 20, 150);
        obstacle1.setFill(Color.GRAY);
        Rectangle obstacle2 = new Rectangle(50, 150, 250, 20);
        obstacle2.setFill(Color.GRAY);

        obstacles = new Group(obstacle1, obstacle2);
    }

    public Group getObstacles() {
        return obstacles;
    }

    public boolean isValidMove(double x, double y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return false;
        }

        for (Rectangle obstacle : obstacles.getChildren().stream()
                .filter(node -> node instanceof Rectangle)
                .map(node -> (Rectangle) node)
                .collect(Collectors.toList())) {
            if (obstacle.getBoundsInParent().intersects(x, y, 20, 50)) {
                return false;
            }
        }

        return true;
    }

}

