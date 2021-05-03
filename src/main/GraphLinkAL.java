package main;

public class GraphLinkAL<T, K> {
    public GraphNodeAL<T, K> destNode;
    public int cost;

    public GraphLinkAL(GraphNodeAL<T, K> destNode, int cost) {
        this.destNode=destNode;
        this.cost=cost;
    }

}
