package Agents;

import Behaviors.Navigation.NavigationBehavior;
import Variables.Graph;
import Variables.Node;
import jade.core.AID;
import jade.core.Agent;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class Navigator extends Agent {
    public static AID IDENTIFIER = new AID("Navigator", AID.ISLOCALNAME);
    private Graph map;

    private Map<Node, Rectangle> nodeToRectangle;

    public Rectangle nodeToRectangle(Node node) {
        return nodeToRectangle.get(node);
    }

    public Navigator() {

    }

    public Graph getMap() {
        return this.map;
    }
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null) {
            this.map = (Graph) args[0];
            nodeToRectangle = (Map<Node, Rectangle>) args[1];
        }
        System.out.println("Navigator agent " + getAID().getName() + " is ready.");

        // Add a behaviour that listens for incoming requests
        addBehaviour(new NavigationBehavior(this));
    }
}

