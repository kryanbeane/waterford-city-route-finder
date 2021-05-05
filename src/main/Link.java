package main;

public class Link {
    private Node<?> destNode;
    private int cost;

    public Link(Node<?> destNode, int cost) {
        this.destNode=destNode;
        this.cost=cost;
    }

    public Node<?> getDestNode() {
        return destNode;
    }

    public void setDestNode(Node<?> destinationNode) {
        this.destNode=destinationNode;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if(cost<0) cost=0;
        this.cost=cost;
    }
}
