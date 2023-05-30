package Behaviors.Transportation;

import Agents.Transporter;
import Variables.Node;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestNavigationBehaviour extends OneShotBehaviour {
    private Transporter transporter;
    private Node startNode;
    private Node destinationNode;

    public RequestNavigationBehaviour(Transporter a, Node startNode, Node destinationNode) {
        super(a);
        this.transporter = a;
        this.startNode = startNode;
        this.destinationNode = destinationNode;
    }

    @Override
    public void action() {
        // Prepare the content of the message
        System.out.println("Start Node " + startNode);
        String start = startNode.getX() + "," + startNode.getY() + "," + startNode.getWeight();
        String destination = destinationNode.getX() + "," + destinationNode.getY() + "," + destinationNode.getWeight();

        String content = "start:" + start + ";destination:" + destination;

        // Create the message
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(Agents.Navigator.IDENTIFIER);
        msg.setContent(content);

        // Send the message
        myAgent.send(msg);
    }
}
