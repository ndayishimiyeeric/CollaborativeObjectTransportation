package Behaviors.Navigation;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Variables.Node;
import Variables.Graph;
import Agents.Navigator;

public class NavigationBehavior extends CyclicBehaviour {
    private final Graph map;

    public NavigationBehavior(Navigator a) {
        super(a);
        this.map = a.getMap(); // get map from Navigator agent
    }

    public void action() {
        // Listen for request messages
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String content = msg.getContent();
            String[] parts = content.split(";");

            String[] startParts = parts[0].split(":")[1].split(",");
            int startX = Integer.parseInt(startParts[0]);
            int startY = Integer.parseInt(startParts[1]);
            double startWeight = Double.parseDouble(startParts[2]);

            String[] destinationParts = parts[1].split(":")[1].split(",");
            int destinationX = Integer.parseInt(destinationParts[0]);
            int destinationY = Integer.parseInt(destinationParts[1]);
            double destinationWeight = Double.parseDouble(destinationParts[2]);

            Node start = new Node(startX, startY, startWeight, null);
            Node destination = new Node(destinationX, destinationY, destinationWeight, null);

            String requestType = parts[2].split(":")[1];

            System.out.println("Start" + start+ ".");
            System.out.println("Destination" + destination+ ".");
            System.out.println("Type:" + requestType);

            System.out.println("map" + map);

            // Create and add a PathFindingBehavior
            myAgent.addBehaviour(new PathFindingBehavior(myAgent, map, start, destination, msg, requestType));
        } else {
            // If no message was received, block the behaviour
            block();
        }
    }
}
