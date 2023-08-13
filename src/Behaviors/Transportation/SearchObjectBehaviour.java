package Behaviors.Transportation;

import Agents.Transporter;
import Variables.Node;
import jade.core.behaviours.OneShotBehaviour;

public class SearchObjectBehaviour extends OneShotBehaviour {
    private final Transporter transporter;

    public SearchObjectBehaviour(Transporter a) {
        super(a);
        this.transporter = a;
    }

    @Override
    public void action() {
        // Get the map from the transporter agent
        Node[][] map = transporter.getMap().getNodes();

        Node objectLocation = null;

        // Scan the entire map for the object
        for (Node[] row : map) {
            for (Node node : row) {
                if (node.isObject()) {
                    objectLocation = node;
                    break;
                }
            }

            if (objectLocation != null) {
                break;
            }
        }

        if (objectLocation != null) {
            // If the object was found, request navigation to its location
            transporter.addBehaviour(new RequestNavigationBehaviour(transporter, transporter.getStart(), objectLocation, "pickup"));
        }
    }
}
