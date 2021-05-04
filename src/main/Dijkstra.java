package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dijkstra {

    public static <T> CostedPath findCheapestPathDijkstra(Node<?> startNode, T lookingfor) {
        CostedPath cp = new CostedPath();
        List<Node<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        Node<?> currentNode;
        do {
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);
            if (currentNode.data.equals(lookingfor)) {
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;
                while (currentNode != startNode) {
                    boolean foundPrevPathNode = false;
                    for (Node<?> n : encountered)
                        for (Link e : n.adjList)
                            if (e.destNode == currentNode && currentNode.nodeValue-e.cost==n.nodeValue) {
                                cp.pathList.add(0,n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        if (foundPrevPathNode) break;
                }
                for (Node<?> n : encountered) n.nodeValue=Integer.MAX_VALUE;
                for (Node<?> n : unencountered) n.nodeValue=Integer.MAX_VALUE;
                return cp;
            }
            for (Link e : currentNode.adjList)
                if (!encountered.contains(e.destNode)) {
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue+e.cost);
                    unencountered.add(e.destNode);
                }
            unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));
        } while (!unencountered.isEmpty());
        return null;
    }

}
