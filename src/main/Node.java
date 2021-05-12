package main;
import java.util.ArrayList;

public class Node<T>{
    public T data;
    public int nodeValue = Integer.MAX_VALUE;
    public double x,y;
    public ArrayList<Link> adjList;
    public ArrayList<Node<T>> adjNodeList;

    public Node(double x,double y) {
        this.adjList=new ArrayList<>();
        this.x=x;
        this.y=y;
    }

    public Node(Landmark landmark) {
        this.data=(T)landmark;
        this.adjList=new ArrayList<>();
        this.x=landmark.x;
        this.y=landmark.y;
    }

    public void connectToNodeDirected(Node<T> src, Node<T> dest, int cost) {
        adjList.add(new Link(src, dest, cost));
    }

    public void connectToNodeUndirected(Node<T> src, Node<T> dest, int cost) {
        adjList.add(new Link(src, dest, cost));
        dest.adjList.add(new Link(dest, src, cost));
    }

    public void connectToNodeUndirected(Node<?> src, Node<?> dest, int cost, boolean hist, boolean easy) {
        adjList.add(new Link(src, dest, cost, hist, easy));
        dest.adjList.add(new Link(dest, src, cost, hist,easy));
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
