import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Playground extends Pane {
    private int width;
    private int height;

    public Playground(int width, int height) {
        this.width = width;
        this.height = height;
        initializePlayground();
    }

    private void initializePlayground() {
        // Draw the playground background
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(Color.LIGHTGRAY);
        getChildren().add(background);

        // Draw any obstacles or additional elements here
    }
}