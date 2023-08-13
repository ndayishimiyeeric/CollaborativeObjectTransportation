package Agents;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import Variables.Node;

public class ObjectBox {
    private Node start;
    private final ImageView imageView;

    public ObjectBox(Node start) {
        this.start = start;

        Image objectImage = new Image("resources/download.png");
        imageView = new ImageView(objectImage);
        // Set the position and size of the ImageView
        imageView.setX(start.getX()*20 + 1);
        imageView.setY(start.getY()*20 + 1);
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Node getStart() {
        return this.start;
    }

    public void setStart(Node currentNode) {
        this.start = currentNode;
    }
}
