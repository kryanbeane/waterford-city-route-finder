package main;
import java.util.*;

public class GraphNodeAL<T,K> {
    public T xCoord;
    public K yCoord;
    public List<GraphNodeAL<T, K>> adjList = new ArrayList<>();

    public GraphNodeAL(T xCoord, K yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void connectToNodeDirected(GraphNodeAL<T, K> destNode) {
        adjList.add(destNode);
    }

    public void connectToNodeUndirected(GraphNodeAL<T, K> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }
}

