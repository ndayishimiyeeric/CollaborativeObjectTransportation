package Behaviors.Transportation;

import Agents.Transporter;
import Agents.ObjectBox;
import Variables.Dijkstra;
import Variables.Node;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MovementBehaviour extends CyclicBehaviour {
    private final Transporter transporter;

    public MovementBehaviour(Agent a) {
        super(a);
        this.transporter = (Transporter) a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.INFORM) {
                // Extract the path from the message content
                String pathString = msg.getContent();
                List<Node> path = stringToPath(pathString);

                transporter.setCurrentNode(path.get(path.size() -1));

                // Move the transporter along the path
                for (Node node : path) {
                    moveTransporterTo(node);
                }
            } else {
                // Handle failure case...
            }
            if (msg.getPerformative() == ACLMessage.REQUEST) {
                String content = msg.getContent();
                String[] parts = content.split(";");
                String[] destinationParts = parts[0].split(":")[1].split(",");
                int destinationX = Integer.parseInt(destinationParts[0]);
                int destinationY = Integer.parseInt(destinationParts[1]);
                double destinationWeight = Double.parseDouble(destinationParts[2]);


                Node destination = new Node(destinationX, destinationY, destinationWeight, null);
                System.out.println("Requested " + destination);

                transporter.addBehaviour(new MoveObjectBehaviour(transporter, destination, transporter.getObjectBox()));
            }
        } else {
            block();
        }
    }

    // Converts a String in the format "(x1,y1) -> (x2,y2) -> ..." to a List of Node objects
    private List<Node> stringToPath(String pathString) {
        List<Node> path = new ArrayList<>();
        String[] nodeStrings = pathString.split(" -> ");
        for (String nodeString : nodeStrings) {
            // Parse the x and y values from the string
            int x = Integer.parseInt(nodeString.substring(1, nodeString.indexOf(',')));
            int y = Integer.parseInt(nodeString.substring(nodeString.indexOf(',') + 1, nodeString.length() - 1));

            // Create a new Node with these values and add it to the path
            Node node = new Node(x, y, 1.0, null); // Assuming a default weight and no Graph
            path.add(node);
        }
        return path;
    }

    // Updates the position of the transporter in the GUI
    private void moveTransporterTo(Node node) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Assuming each grid cell is 20 pixels
                int cellSize = 20;
                // Update the position of the transporter's ImageView
                transporter.getImageView().setX(node.getX() * cellSize + 1);
                transporter.getImageView().setY(node.getY() * cellSize + 1);
            }
        });

        // Pause for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
