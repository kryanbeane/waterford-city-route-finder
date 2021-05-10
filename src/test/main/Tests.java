package main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {
    Search search;
    Node<Integer> n1, n2, n3, n4, n5, n6;
    Link l;

    @BeforeEach
    void setup() {
        search = new Search();
        n1 = new Node<>(1);
        n2 = new Node<>(2);
        n3 = new Node<>(3);
        n4 = new Node<>(4);
        n5 = new Node<>(5);
        n6 = new Node<>("NodeName", 1,2);
        n1.connectToNodeUndirected(n2, 5);
        n2.connectToNodeUndirected(n3, 5);
        n3.connectToNodeUndirected(n4, 5);
        n4.connectToNodeUndirected(n5, 5);

        l=new Link(n1, 20);
    }

    @Test
    void dijkstraShouldReturnCostedPath() {
        assertEquals(5, Search.dijkstra(n1, n2).pathCost);
    }

    @Test
    void getDestNodeShouldReturnTheNodeAttachedToALink() {
        assertEquals(n1, l.getDestNode());
    }

    @Test
    void getCostShouldReturnALinksCost() {
        assertEquals(20, l.getCost());
    }

    @Test
    void setDestNodeShouldChangeTheNodeAttachedToALink() {
        l.setDestNode(n2);
        assertEquals(n2, l.getDestNode());
    }

    @Test
    void setCostShouldChangeALinksCost() {
        l.setCost(25);
        assertEquals(25, l.getCost());
    }

    @Test
    void getXCoordShouldReturnNodesXCoordinate() {
        assertEquals(n6.getXCoord(), 1);
    }

    @Test
    void getYCoordShouldReturnNodesYCoordinate() {
        assertEquals(n6.getYCoord(), 2);
    }

    @Test
    void setXCoordShouldChangeNodesXCoordinate() {
        n6.setXCoord(4);
        assertEquals(n6.getXCoord(), 4);
    }

    @Test
    void setYCoordShouldChangeNodesYCoordinate() {
        n6.setYCoord(5);
        assertEquals(n6.getYCoord(), 5);
    }

    @Test
    void getNodeNameShouldReturnANodesName() {
        assertEquals(n6.getNodeName(), "NodeName");
    }

    @Test
    void setNodeNameShouldChangeANodesName() {
        n6.setNodeName("newName");
        assertEquals(n6.getNodeName(), "newName");
    }

}