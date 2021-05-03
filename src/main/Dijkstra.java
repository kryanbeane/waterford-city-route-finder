package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dijkstra {

    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL<?> startNode, T lookingfor) {
        CostedPath cp = new CostedPath();
        List<GraphNodeAL<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        startNode.nodeValue = 0;
        unencountered.add(startNode);
        GraphNodeAL<?> currentNode;
        do {
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);
            if (currentNode.data.equals(lookingfor)) {
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;
                while (currentNode != startNode) {
                    boolean foundPrevPathNode = false;
                    for (GraphNodeAL<?> n : encountered)
                        for (GraphLinkAL e : n.adjList)
                            if (e.destNode == currentNode && currentNode.nodeValue-e.cost==n.nodeValue) {
                                cp.pathList.add(0,n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        if (foundPrevPathNode) break;
                }
                for (GraphNodeAL<?> n : encountered) n.nodeValue=Integer.MAX_VALUE;
                for (GraphNodeAL<?> n : unencountered) n.nodeValue=Integer.MAX_VALUE;
                return cp;
            }
            for (GraphLinkAL e : currentNode.adjList)
                if (!encountered.contains(e.destNode)) {
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue+e.cost);
                    unencountered.add(e.destNode);
                }
            unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));
        } while (!unencountered.isEmpty());
        return null;
    }

}
