package main;
import java.util.*;

public class GraphNodeAL<T> {
    public T data;
    public int nodeValue = Integer.MAX_VALUE;
    public List<GraphLinkAL> adjList = new ArrayList<>();

    public GraphNodeAL(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNodeAL<T> destNode, int cost) {
        adjList.add(new GraphLinkAL(destNode, cost));
    }

    public void connectToNodeUndirected(GraphNodeAL<T> destNode, int cost) {
        adjList.add(new GraphLinkAL(destNode, cost));
        destNode.adjList.add(new GraphLinkAL(this, cost));
    }
}

