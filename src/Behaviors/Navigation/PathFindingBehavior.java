package Behaviors.Navigation;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import Variables.Dijkstra;
import Variables.Node;
import Variables.Graph;
import java.util.List;
import Agents.Navigator;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PathFindingBehavior extends OneShotBehaviour {
    private final Graph map;
    private final Node start;
    private final Node destination;
    private final ACLMessage request;
    private final Navigator navigator;
    private final String requestType;

    public PathFindingBehavior(Agent a, Graph map, Node start, Node destination, ACLMessage request, String requestType) {
        super(a);
        this.map = map;
        this.start = start;
        this.destination = destination;
        this.request = request;
        this.navigator = (Navigator) a;
        this.requestType = requestType;
    }

    public void action() {
        // Calculate the shortest path
        System.out.println("map"+ map);
        List<Node> path = Dijkstra.findShortestPath(map, start, destination);
        System.out.println("Path " + path);

        assert path != null;
        for (Node node : path) {
            node.setIsAgentPath(true);
            Rectangle agentRect = navigator.nodeToRectangle(node);
            Platform.runLater(() -> agentRect.setFill(Color.PURPLE));
        }

        // Convert the path to a String
        String pathString = pathToString(path);

        // Create a reply message
        ACLMessage reply = request.createReply();
        // If a path was found, set the content of the reply to the path
        reply.setPerformative(ACLMessage.INFORM);
        String contentWithRequestType = "type:" + requestType + ";path:" + pathString;
        reply.setContent(contentWithRequestType);

        // Send the reply
        myAgent.send(reply);
    }

    // Converts a List of Node objects to a String in the format "(x1,y1) -> (x2,y2) -> ..."
    private String pathToString(List<Node> path) {
        StringBuilder sb = new StringBuilder();
        for (Node node : path) {
            sb.append("(").append(node.getX()).append(",").append(node.getY()).append(") -> ");
        }
        // Remove the last " -> "
        if (sb.length() > 4) {
            sb.setLength(sb.length() - 4);
        }
        return sb.toString();
    }
}
