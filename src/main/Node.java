package main;
import java.util.*;

public class Node<T> {
    private T data;
    private int xCoord, yCoord;
    private String name;
    private int nodeValue=Integer.MAX_VALUE;
    private final List<Node<T>> adjList=new ArrayList<>();
    private final List<Link> adjListLink=new ArrayList<>();

    public Node(T data) {
        this.data=data;
    }

    public Node(String nodeName, int xCoord, int yCoord) {
        setNodeName(nodeName);
        setXCoord(xCoord);
        setYCoord(yCoord);
    }

    public Node(T data, String nodeName, int xCoord, int yCoord) {
        this.data=data;
        setNodeName(nodeName);
        setXCoord(xCoord);
        setYCoord(yCoord);
    }

    public void connectToNodeDirectedDijkstra(Node<?> destNode, int cost) {
        adjListLink.add(new Link(destNode,cost));
    }

    public void connectToNodeDirectedBFS(Node<?> destNode) {
        adjList.add((Node<T>) destNode);
    }

    public void connectToNodeUndirected(Node<T> destNode, int cost) {
        adjListLink.add(new Link(destNode,cost));
        destNode.adjListLink.add(new Link(this,cost));
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        if(xCoord<0) return;
        this.xCoord=xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        if (yCoord<0) return;
        this.yCoord=yCoord;
    }

    public String getNodeName() {
        return name;
    }

    public void setNodeName(String name) {
        if (name.length()>24) return;
        this.name=name;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data=data;
    }

    public List<Node<T>> getAdjList() {
        return adjList;
    }

    public List<Link> getAdjListLink() {
        return adjListLink;
    }
}

