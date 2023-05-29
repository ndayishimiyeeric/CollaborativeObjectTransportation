package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class Navigator extends Agent {
    public static AID IDENTIFIER = new AID("Navigator", AID.ISLOCALNAME);

    @Override
    protected void setup() {
        System.out.println("Navigator agent " + getAID().getName() + " is ready.");

        // Add a one-shot behaviour as an example
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Navigator agent " + myAgent.getAID().getName() + " performed its one-shot action.");
            }
        });
    }
}

