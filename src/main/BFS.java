package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BFS {

    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(GraphNodeAL<?> startNode, T lookingfor) {
        List<List<GraphNodeAL<?>>> agenda = new ArrayList<>();
        List<GraphNodeAL<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda,null, lookingfor);
        Collections.reverse(Objects.requireNonNull(resultPath));
        return resultPath;
    }

    public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(List<List<GraphNodeAL<?>>> agenda, List<GraphNodeAL<?>> encountered, T lookingfor){
        if (agenda.isEmpty()) return null; //Search failed

        List<GraphNodeAL<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL<?> currentNode = nextPath.get(0); //The first item in the next path is the current node

        if (currentNode.data.equals(lookingfor)) return nextPath; //If that's the goal, we've found our path (so return it)

        if (encountered == null) encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list

        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again

        for (GraphNodeAL<?> adjNode : currentNode.adjList) //For each adjacent node
            if (!encountered.contains(adjNode)) { //If it hasn't already been encountered
                List<GraphNodeAL<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of
                //the current/next path
                newPath.add(0,adjNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }

}