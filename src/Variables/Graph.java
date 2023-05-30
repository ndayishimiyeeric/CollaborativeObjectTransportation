package Variables;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Graph implements Serializable {
    private final Node[][] nodes;

    public Graph(int width, int height) {
        this.nodes = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                nodes[i][j] = new Node(i, j, 1.0, this); // default weight set to 1

            }
        }
    }

    public Node getNode(int x, int y) {
        if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight()) {
            return null;
        }
        return nodes[x][y];
    }

    public Node[][] getNodes() {
        return this.nodes;
    }

    public int getWidth() {
        return nodes.length;
    }

    public int getHeight() {
        return nodes[0].length;
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            Node neighbor = getNode(node.getX() + dx[i], node.getY() + dy[i]);

            if (neighbor != null && !neighbor.isObstacle()) {
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }
}



