package main;
import java.util.*;

public class GraphNodeAL<T> {
    public T data;
    public List<GraphNodeAL<T>> adjList = new ArrayList<>();

    public GraphNodeAL(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
    }

    public void connectToNodeUndirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }
}

