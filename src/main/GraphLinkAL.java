package main;

public class GraphLinkAL {
    public GraphNodeAL<?> destNode;
    public int cost;

    public GraphLinkAL(GraphNodeAL<?> destNode, int cost) {
        this.destNode = destNode;
        this.cost = cost;
    }
}
