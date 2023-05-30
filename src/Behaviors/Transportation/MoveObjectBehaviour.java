package Behaviors.Transportation;

import Agents.Transporter;
import Agents.ObjectBox;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Variables.Node;
import Variables.Dijkstra;
import javafx.application.Platform;

import java.util.List;

public class MoveObjectBehaviour extends CyclicBehaviour {
    private Transporter transporter;
    private Node destination;
    private ObjectBox box;

    public MoveObjectBehaviour(Agent a, Node destination, ObjectBox box) {
        super(a);
        this.transporter = (Transporter) a;
        this.destination = destination;
        this.box = box;
    }

    @Override
    public void action() {
        List<Node> path = Dijkstra.findShortestPath(transporter.getMap(), transporter.getCurrentNode(), destination);

        // Initialize first move for the ObjectBox
        Node firstNode = path.get(0);
        moveObjectTo(firstNode);
        path.remove(0); // remove the first node from the path

        for (Node node : path) {
            // Move the Transporter to the ObjectBox's position
            moveTransporterTo(box.getStart());
            // Then, move the ObjectBox to the next node
            moveObjectTo(node);

            // Add some delay for visualization purposes
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Lastly, move the Transporter to the ObjectBox's position
        moveTransporterTo(box.getStart());
        this.myAgent.removeBehaviour(this);
    }

    private void moveObjectTo(Node node) {
        Platform.runLater(() -> {
            // Assuming each grid cell is 20 pixels
            int cellSize = 20;
            // Update the position of the box's ImageView
            box.getImageView().setX(node.getX() * cellSize + 1);
            box.getImageView().setY(node.getY() * cellSize + 1);
        });

        // Update box's current node
        box.setStart(node);

        // Pause for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveTransporterTo(Node node) {
        Platform.runLater(() -> {
            // Assuming each grid cell is 20 pixels
            int cellSize = 20;
            // Update the position of the transporter's ImageView
            transporter.getImageView().setX(node.getX() * cellSize + 1);
            transporter.getImageView().setY(node.getY() * cellSize + 1);
        });

        // Update transporter's current node
        transporter.setCurrentNode(node);

        // Pause for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

