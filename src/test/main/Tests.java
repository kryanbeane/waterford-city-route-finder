package main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {
    CostedPath cp;
    Landmark lm1, lm2;
    Node<?> n1, n2;
    Link link;

    @BeforeEach
    void setup() {
        lm1 = new Landmark(5, 5, "Landmark One");
        lm2 = new Landmark(10, 10, "Landmark Two");
        n1 = new Node<>(lm1);
        n2 = new Node<>(lm2);
        link = new Link(n1,n2,10);
        cp = new CostedPath();
        cp.setPathCost(10);
        cp.pathList.add(n1);
        cp.pathList.add(n2);
    }

    @Test
    void getPathCostShouldReturnCostOfAPath() {
        assertEquals(10, cp.getPathCost());
    }

    @Test
    void setPathCostShouldChangeCostOfAPath() {
        cp.setPathCost(25);
        assertEquals(25, cp.getPathCost());
    }

    @Test
    void getPathListShouldReturnNodeListOfAPath() {
        List<Node<?>> a = new ArrayList<>();
        a.add(n1);
        a.add(n2);
        assertEquals(a, cp.getPathList());
    }

    @Test
    void setPathListShouldChangeNodeListOfAPath() {
        List<Node<?>> a = new ArrayList<>();
        Node<?> n3 = new Node<>(lm1); Node<?> n4 = new Node<>(lm1);
        a.add(n3);a.add(n4);
        cp.setPathList(a);
        assertEquals(a, cp.getPathList());
    }

    @Test
    void getXShouldReturnLandmarkXCoord() {
        assertEquals(5, lm1.getX());
    }

    @Test
    void setXShouldChangeLandmarkXCoord() {
        lm1.setX(10);
        assertEquals(10, lm1.getX());
    }

    @Test
    void getYShouldReturnLandmarkYCoord() {
        assertEquals(5, lm1.getY());
    }

    @Test
    void setYShouldChangeLandmarkYCoord() {
        lm1.setY(10);
        assertEquals(10, lm1.getY());
    }

    @Test
    void getLandmarkNameShouldReturnLandmarksName() {
        assertEquals("Landmark One", lm1.getLandmarkName());
    }

    @Test
    void setLandmarkNameShouldChangeLandmarksName() {
        lm1.setLandmarkName("New Name");
        assertEquals("New Name", lm1.getLandmarkName());
    }



}