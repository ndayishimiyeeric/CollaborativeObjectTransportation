package Agents;

import Variables.Graph;
import jade.core.Agent;
import jade.core.AID;
//import Behaviors.Transportation.SendStatusBehaviour;
import Behaviors.Transportation.MovementBehaviour;
import Behaviors.Transportation.SearchObjectBehaviour;
import Behaviors.Transportation.MoveObjectBehaviour;
import Variables.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Transporter extends Agent {
    private Graph map;
    private Node start;
    public static AID IDENTIFIER = new AID("Transporter", AID.ISLOCALNAME);
    private Node currentNode;
    private final ImageView imageView;

    private ObjectBox objectBox;

    // Add a constructor that takes the map as a parameter
    public Transporter(Node start) {
        this.start = start;
        Image transporterImage = new Image("resources/agent2.jpg");
        imageView = new ImageView(transporterImage);
        // Set the position and size of the ImageView
        imageView.setX(start.getX()*20 + 1);
        imageView.setY(start.getY()*20 + 1);
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Graph getMap() {
        return this.map;
    }

    public Node getStart() {
        return this.start;
    }

    public ObjectBox getObjectBox() {
        return this.objectBox;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            this.map = (Graph) args[0];
            this.start = (Node) args[1];
            this.objectBox = (ObjectBox) args[2];
        }
        System.out.println("Transporter agent " + getAID().getName() + " is ready.");

        // Add behaviors
        addBehaviour(new MovementBehaviour(this));
        addBehaviour(new SearchObjectBehaviour(this));
    }
}
