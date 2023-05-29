package Agents;

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

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Node end;

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

        // Generate random start nodes
        Node start = nodes[(int)(Math.random()*graph.getWidth())][(int)(Math.random()*graph.getHeight())];

        // Ensure start and end are not obstacles
        start.setObstacle(false);

        Group root = new Group();
        int rectSize = 18; // increase the size of rectangles

        for (Node[] row : nodes) {
            for (Node node : row) {
                Rectangle rect = new Rectangle(node.getX()*20 + 1, node.getY()*20 + 1, rectSize, rectSize); // adjust positions to account for larger rectangles
                if (node.isObstacle()) {
                    rect.setFill(Color.RED);
                } else if (node.equals(start)) {
                    rect.setFill(Color.GREEN); // Highlight the start nodes
                } else {
                    rect.setFill(Color.WHITE);
                }

                nodeToRectangle.put(node, rect); // Link each Node with its corresponding Rectangle

                Node[] startWrapper = new Node[1];
                startWrapper[0] = start;

                // Add a mouse clicked event listener
                rect.setOnMouseClicked(event -> {
                    if(end != null) {
                        end.setObstacle(false); // Reset the old end node if exists
                    }
                    end = node; // Assign the new end node
                    end.setObstacle(false); // Ensure end node is not an obstacle
                    rect.setFill(Color.BLUE); // Highlight the end node
                    List<Node> path = Dijkstra.findShortestPath(graph, startWrapper[0], end);

                    if (path != null) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Delay for 2 seconds
                        pause.setOnFinished(event2 -> { // Action to execute after delay
                            for (Node pathNode : path) {
                                Rectangle pathRect = nodeToRectangle.get(pathNode);
                                pathRect.setFill(Color.PURPLE); // Highlight the path nodes
                            }
                            // After drawing the path, set the start node to the current end node
                            startWrapper[0].setObstacle(false); // Ensure the start node is not an obstacle
                            Rectangle startRect = nodeToRectangle.get(startWrapper[0]);
                            startRect.setFill(Color.WHITE); // Set the color of the old start node back to WHITE
                            startWrapper[0] = end; // Set the start node to the current end node
                            startRect = nodeToRectangle.get(startWrapper[0]);
                            startRect.setFill(Color.GREEN); // Highlight the new start node
                        });
                        pause.play();
                    }
                });

                root.getChildren().add(rect);
            }
        }

        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
