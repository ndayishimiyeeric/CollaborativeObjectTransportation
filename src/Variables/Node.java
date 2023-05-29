package Variables;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {

    private int x;
    private int y;
    private boolean isObstacle;
    private Graph map;
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

    //public void setObstacle(boolean isObstacle) {
    //    this.isObstacle = isObstacle;
    // }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    // Get all neighbors of this node from the map
    public List<Node> getNeighbors() {
        return map.getNeighbors(this);
    }
}


