package Variables;
import java.util.*;

public class Dijkstra {

    private static double getDistance(Node node1, Node node2) {
        return Math.hypot(node1.getX() - node2.getX(), node1.getY() - node2.getY());
    }

    public static List<Node> findShortestPath(Graph graph, Node start, Node end) {
        Map<Node, Double> gScore = new HashMap<>();
        Comparator<Node> nodeComparator = Comparator.comparingDouble(gScore::get);
        PriorityQueue<Node> openSet = new PriorityQueue<>(nodeComparator);
        HashSet<Node> openSetLookup = new HashSet<>(); // to check node existence in O(1)
        Map<Node, Node> cameFrom = new HashMap<>();

        // Initialize every node's gScore with infinity, except for the start node
        for (Node[] row : graph.getNodes()) {
            for (Node node : row) {
                gScore.put(node, Double.MAX_VALUE);
            }
        }

        openSet.add(start);
        openSetLookup.add(start);
        gScore.put(start, 0.0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            openSetLookup.remove(current); // maintain the openSetLookup

            if (current.equals(end)) {
                return reconstructPath(cameFrom, current);
            }

            for (Node neighbor : current.getNeighbors()) {
                if (neighbor.isObstacle()) continue;

                double tentativeGScore = gScore.get(current) + getDistance(current, neighbor);
                if (tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    if (!openSetLookup.contains(neighbor)) {
                        openSet.add(neighbor);
                        openSetLookup.add(neighbor);
                    }
                }
            }


            //System.out.println("Open set: " + openSet);
            //System.out.println("G Score: " + gScore);
            //System.out.println("Came From: " + cameFrom);
        }
        return null; // No path was found
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        Collections.reverse(totalPath);
        return totalPath;
    }
}