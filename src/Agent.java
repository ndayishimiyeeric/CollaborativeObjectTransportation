import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Agent {
    private double x;
    private double y;
    private Group figure;

    public Agent(double x, double y) {
        this.x = x;
        this.y = y;
        createFigure();
    }

    private void createFigure() {
        Circle head = new Circle(0, 0, 10, Color.BLUE);
        Rectangle body = new Rectangle(-5, 10, 10, 20);
        Rectangle leftLeg = new Rectangle(-5, 30, 5, 20);
        Rectangle rightLeg = new Rectangle(0, 30, 5, 20);
        Rectangle leftArm = new Rectangle(-15, 10, 10, 5);
        Rectangle rightArm = new Rectangle(5, 10, 10, 5);

        figure = new Group(head, body, leftLeg, rightLeg, leftArm, rightArm);
    }

    public Group getFigure() {
        return figure;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
