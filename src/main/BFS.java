package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BFS {

    public static <T> List<Node<?>> findPathBreadthFirst(Node<?> startNode, T lookingfor) {
        List<List<Node<?>>> agenda = new ArrayList<>();
        List<Node<?>> firstAgendaPath = new ArrayList<>(),resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda,null,lookingfor);
        Collections.reverse(Objects.requireNonNull(resultPath));
        return resultPath;
    }

    public static <T> List<Node<?>> findPathBreadthFirst(List<List<Node<?>>> agenda, List<Node<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null;
        List<Node<?>> nextPath = agenda.remove(0);
        Node<?> currentNode=nextPath.get(0);
        if (currentNode.data.equals(lookingfor)) return nextPath;
        if (encountered == null) encountered = new ArrayList<>();
        encountered.add(currentNode);
        for (Node<?> adjNode : currentNode.adjList)
            if (!encountered.contains(adjNode)) {
                List<Node<?>> newPath = new ArrayList<>(nextPath);
                newPath.add(0,adjNode);
                agenda.add(newPath);
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor);
    }


}