public class TransporterAgent extends Agent {

    public TransporterAgent(double x, double y) {
        super(x, y);
    }

    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
