package Agents;

import Behaviors.Navigation.NavigationBehavior;
import Variables.Graph;
import Variables.Node;
import jade.core.AID;
import jade.core.Agent;

public class Navigator extends Agent {
    public static AID IDENTIFIER = new AID("Navigator", AID.ISLOCALNAME);
    private Graph map;

    public Navigator() {

    }

    public Graph getMap() {
        return this.map;
    }
    protected void setup() {
        Object[] args = getArguments();
        if (args != null) {
            this.map = (Graph) args[0];
        }
        System.out.println("Navigator agent " + getAID().getName() + " is ready.");

        // Add a behaviour that listens for incoming requests
        addBehaviour(new NavigationBehavior(this));
    }
}

