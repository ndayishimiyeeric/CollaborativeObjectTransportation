package Variables;

import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {

    private final int x;

    private final int y;
    private boolean isObstacle;

    private boolean isObject;

    private boolean isAgentPath;

    private boolean isLocked;

    private boolean isTerminal;

    private final Graph map;
    private double weight;

    private double cost;
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.weight, other.weight);
    }
    public Node(int x, int y, double weight, Graph map) {
        this.x = x;
        this.y = y;
        this.isObstacle = false;
        this.isLocked = false;
        boolean isTerminal = false;
        this.weight = weight;
        this.map = map;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public boolean isLocked() {return isLocked;}

    public boolean isTerminal() {return isTerminal;}

    public boolean isObject() {
        return isObject;
    }

    public boolean isAgentPath() {
        return isAgentPath;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public void setIsLocked(boolean isLocked) {this.isLocked = isLocked;}

    public void setIsTerminal(boolean isTerminal) {this.isTerminal = isTerminal;}

    public void setObject(boolean isObject) {this.isObject = isObject;}

    public void setIsAgentPath(boolean isAgentPath) {this.isObject = isAgentPath; }


    // Get all neighbors of this node from the map
    public List<Node> getNeighbors() {
        return map.getNeighbors(this);
    }
}


