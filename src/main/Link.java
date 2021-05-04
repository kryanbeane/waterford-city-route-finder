package main;

public class Link {
    public Node<?> destNode;
    public int cost;

    public Link(Node<?> destNode, int cost) {
        this.destNode = destNode;
        this.cost = cost;
    }
}
