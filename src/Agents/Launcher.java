package Agents;

// Jade core
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import Variables.Graph;
import Variables.Node;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import Variables.Dijkstra;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Node end;

    private ContainerController container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Graph graph = new Graph(40, 30);
        Node[][] nodes = graph.getNodes();
        Map<Node, Rectangle> nodeToRectangle = new HashMap<>(); // Map to track Nodes and their corresponding Rectangles

        // Create 20 obstacles at random positions
        for(int i=0; i<400; i++) {
            int x = (int)(Math.random()*graph.getWidth());
            int y = (int)(Math.random()*graph.getHeight());
            nodes[x][y].setObstacle(true);
        }

        // Initialize JADE container
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        container = runtime.createMainContainer(profile);

        // Generate random start nodes
        Node start = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];

        // Ensure start and end are not obstacles
        start.setObstacle(false);

        Group root = new Group();
        int rectSize = 18; // increase the size of rectangles

        // Generate random start nodes
        Node transporterStart = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];
        while(transporterStart.isObstacle()) { // Ensure start node is not an obstacle
            transporterStart = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];
        }

        Transporter transporter = new Transporter(transporterStart);

        // Generate random object node
        Node objectNode = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];
        while(objectNode.isObstacle() || objectNode.equals(start)) { // Ensure object node is not an obstacle or the start node
            objectNode = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];
        }
        objectNode.setObject(true);
        ObjectBox objectBox = new ObjectBox(objectNode);

        for (Node[] row : nodes) {
            for (Node node : row) {
                Rectangle rect = new Rectangle(node.getX()*20 + 1, node.getY()*20 + 1, rectSize, rectSize); // adjust positions to account for larger rectangles
                if (node.isObstacle()) {
                    rect.setFill(Color.RED);
                } else if (node.isObject()) {
                    rect.setFill(Color.GRAY);
                } else if (node.equals(start)) {
                    rect.setFill(Color.GREEN); // Highlight the start nodes
                } else {
                    rect.setFill(Color.WHITE);
                }

                nodeToRectangle.put(node, rect); // Link each Node with its corresponding Rectangle

                // When a rectangle is clicked
                rect.setOnMouseClicked(event -> {
                    if (end != null) {
                        end.setObstacle(false); // Reset the old end node if exists
                    }
                    end = node; // Assign the new end node
                    end.setObstacle(false); // Ensure end node is not an obstacle
                    rect.setFill(Color.BLUE); // Highlight the end node

                    String destination = end.getX() + "," + end.getY() + "," + end.getWeight();
                    String content = "destination:" + destination + ";box:" + objectBox;

                    // Create a new ACLMessage
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.addReceiver(Transporter.IDENTIFIER);
                    msg.setContent(content);
                    // Send the message
                    transporter.send(msg);
                });

                root.getChildren().add(rect);
            }
        }

        root.getChildren().add(objectBox.getImageView());


        // Initialize Transporter agent
        Object[] transporterArgs = new Object[]{ graph, transporterStart, objectBox };
        //Transporter transporter = new Transporter(transporterStart);
        transporter.setArguments(transporterArgs);
        // Create the agent controller for the transporter
        AgentController transporterController = container.acceptNewAgent("Transporter", transporter);
        transporterController.start();
        // Get the ImageView of the Transporter and add it to the root Group
        ImageView transporterImageView = transporter.getImageView();
        root.getChildren().add(transporterImageView);

        // Initialize Navigator agent
        Object[] navigatorArgs = new Object[]{graph};
        AgentController navigator = container.createNewAgent("Navigator", "Agents.Navigator", navigatorArgs);
        navigator.start();



        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
