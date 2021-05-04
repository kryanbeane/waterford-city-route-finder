package main;
import java.util.*;

public class Node<T> {
    public T data;
    public int nodeValue = Integer.MAX_VALUE;
    public List<Link> adjList = new ArrayList<>();
    public Node(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(Node<T> destNode,int cost) {
        adjList.add(new Link(destNode, cost));
    }

    public void connectToNodeUndirected(Node<T> destNode,int cost) {
        adjList.add(new Link(destNode, cost));
        destNode.adjList.add(new Link(this, cost));
    }
}

