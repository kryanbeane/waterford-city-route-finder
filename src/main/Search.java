package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Search {

    public static <T> CostedPath findCheapestPathDijkstra(Node<?> src, T dest, String modifier) {
        CostedPath cp = new CostedPath();
        List<Node<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        src.nodeValue = 0;
        unencountered.add(src);
        Node<?> currentNode;
        do {
            currentNode = unencountered.remove(0);
            encountered.add(currentNode);
            if (currentNode.data.equals(dest)) {
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue;
                while (currentNode != src) {
                    boolean foundPrevPathNode = false;
                    for (Node<?> n : encountered) {
                        for (Link e : n.adjList) {
                            if (e.dest == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) {
                                cp.pathList.add(0, n);
                                currentNode = n;
                                foundPrevPathNode = true;
                                break;
                            }
                        }
                        if (foundPrevPathNode) break;
                    }
                }
                for (Node<?> n : encountered) {
                    n.nodeValue = Integer.MAX_VALUE;
                }
                for (Node<?> n : unencountered) {
                    n.nodeValue = Integer.MAX_VALUE;
                }
                for (Node n : cp.pathList) {
                    for (Object link : n.adjList
                    ) {
                        Link lnk = (Link) link;
                        if (modifier.equals("Historical") && lnk.historical) {
                            lnk.cost = (lnk.cost * 2);
//
                        } else if (modifier.equals("Easiest") && lnk.easiest) {
                            lnk.cost = (int) (lnk.cost * 1.25);
                        }
                    }
                }
                return cp;
            }

            for (Link e : currentNode.adjList)
                if (!encountered.contains(e.dest)) {
                    if (modifier.equals("Historical") && e.historical) {
                        e.cost = (int) (e.cost * 0.5);

                    } else if (modifier.equals("Easiest") && e.easiest) {
                        e.cost = (int) (e.cost * 0.8);
                    }
                    e.dest.nodeValue = Integer.min(e.dest.nodeValue, currentNode.nodeValue + e.cost);
                    unencountered.add(e.dest);
                }
            unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));
        } while (!unencountered.isEmpty());
        return null;
    }

    public static <T> CostedPath findCheapestPathDijkstra(Node<?> src, T lookingFor, ArrayList<Node> avoidList, String modifier) {
        CostedPath cp = new CostedPath();
        List<Node<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
        src.nodeValue = 0;
        unencountered.add(src);
        Node<?> currentNode;
        if (avoidList.isEmpty()) {
            return findCheapestPathDijkstra(src, lookingFor, modifier);
        } else do {
                currentNode = unencountered.remove(0);
                for (Object avoid : avoidList) {
                    if (!currentNode.equals(avoid)) {
                        encountered.add(currentNode);
                        if (currentNode.data.equals(lookingFor)) {
                            cp.pathList.add(currentNode);
                            cp.pathCost = currentNode.nodeValue;
                            while (currentNode != src) {
                                boolean foundPrevPathNode = false;
                                for (Node<?> n : encountered) {
                                    for (Link e : n.adjList)
                                        if (e.dest == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) {
                                            cp.pathList.add(0, n);
                                            currentNode = n;
                                            foundPrevPathNode = true;
                                            break;
                                        }
                                    if (foundPrevPathNode)
                                        break;
                                }
                            }
                            for (Node<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                            for (Node<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;
                            return cp;
                        }
                        for (Link e : currentNode.adjList)
                            if (!encountered.contains(e.dest)) {
                                if (modifier.equals("Historical") && e.historical) {
                                    System.out.println("e.cost inside Historical mod: " + e.cost);
                                    e.cost = (int) (e.cost * 0.5);
                                } else if (modifier.equals("Easiest") && e.easiest) {
                                    e.cost = (int) (e.cost * 0.8);
                                    System.out.println("e.cost inside easy mod: " + e.cost);
                                }
                                e.dest.nodeValue = Integer.min(e.dest.nodeValue, currentNode.nodeValue + e.cost);
                                unencountered.add(e.dest);
                            }
                    }
                }
                unencountered.sort(Comparator.comparingInt(n -> n.nodeValue));
            } while (!unencountered.isEmpty());

            return null;
    }

    public static ArrayList<Integer> bfs(Node<?> src, Node<?> dest, int w, int[] graphArray) {
        ArrayList<Integer> agenda = new ArrayList<>();
        int destIndex = (int) ((dest.y - 1) * w + dest.x);
        int startIndex = (int) ((src.y - 1) * w + src.x);
        agenda.add(startIndex);
        graphArray[startIndex] = 1;
        int v, current;
        do {
            current = agenda.remove(0);
            if (current == destIndex) {
                ArrayList<Integer> newPath = new ArrayList<>();
                int i = destIndex;
                v = graphArray[destIndex];
                if (i == startIndex) {
                    return newPath;
                } else {
                    newPath.add(i);
                    do {
                        if (i-1>=0 && i-1%w!=0 && graphArray[i-1]==v-1) {
                            i-=1;
                            v-=1;
                            newPath.add(i);
                        } else if (i+1<graphArray.length && i%w!=0 && graphArray[i+1]==v-1) {
                            i+=1;
                            v-=1;
                            newPath.add(i);
                        } else if (i-w>=0 && graphArray[i-w]==v-1) {
                            i-=w;
                            v-=1;
                            newPath.add(i);
                        } else if (i+w<graphArray.length && graphArray[i+w]==v-1) {
                            i+=w;
                            v-=1;
                            newPath.add(i);
                        }
                    } while (i!=startIndex);
                    return newPath;
                }
            } else {
                v = graphArray[current];
                if (current+1<graphArray.length && current%w!=0 && graphArray[current+1]==0) {
                    graphArray[current+1] = v+1;
                    agenda.add(current+1);
                }
                if (current-1>=0 && current-1%w!=0 && graphArray[current-1]==0) {
                    graphArray[current-1] = v+1;
                    agenda.add(current-1);
                }
                if (current+w<graphArray.length && graphArray[current+w]==0) {
                    graphArray[current+w] = v+1;
                    agenda.add(current+w);
                }
                if (current-w>=0 && graphArray[current-w]==0) {
                    graphArray[current-w] = v+1;
                    agenda.add(current-w);
                }
            }
        }
        while (!agenda.isEmpty());
        return null;
    }

    public static <T> List<Node<?>> findPathBreadthFirst(Node<?> src, T dest) {
        List<List<Node<?>>> agenda = new ArrayList<>();
        List<Node<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(src);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, dest);
        Collections.reverse(resultPath);
        return resultPath;
    }

    public static <T> List<Node<?>> findPathBreadthFirst(List<List<Node<?>>> agenda, List<Node<?>> encountered, T lookingfor) {
        if (agenda.isEmpty())
            return null;
        List<Node<?>> nextPath = agenda.remove(0);
        Node<?> currentNode = nextPath.get(0);
        if (currentNode.data.equals(lookingfor))
            return nextPath;
        if (encountered == null)
            encountered = new ArrayList<>();
        encountered.add(currentNode);
        for (Node<?> adjNode : currentNode.adjNodeList)
            if (!encountered.contains(adjNode)) {
                List<Node<?>> newPath = new ArrayList<>(nextPath);
                newPath.add(0, adjNode);
                agenda.add(newPath);
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }

    public static ArrayList<Integer> dfs(Node src, Node dest, int width, int[] graphArray) {
        ArrayList<Integer> agenda = new ArrayList<>();
        int destIndex = (int) (dest.y * width + dest.x);
        int startIndex = (int) (src.y * width + dest.x);
        agenda.add(startIndex);
        graphArray[startIndex] = 1;
        int v, current;
        do {
            current = agenda.remove(0);
            if (current == destIndex) {
                int totalDistance = graphArray[current] - 1;
                ArrayList<Integer> newPath = new ArrayList<>();
                int cn = destIndex;
                v = graphArray[destIndex];
                System.out.println("Total Distance using DepthFirstSearch: " + totalDistance);
                newPath.add(0, cn);
                if (cn == startIndex)
                    return newPath;
                else {
                    do {
                        if (cn - 1 >= 0 && cn - 1 % width != 0 && graphArray[cn - 1] == v - 1) {
                            cn = cn - 1;
                            v = v - 1;
                            newPath.add(0,cn);
                        } else if (cn + 1 < graphArray.length && cn % width >= 0 && graphArray[cn + 1] == v - 1) {
                            cn = cn + 1;
                            v = v - 1;
                            newPath.add(0,cn);
                        } else if (cn - width >= 0 && graphArray[cn - width] == v - 1) {
                            cn = cn - width;
                            v = v - 1;
                            newPath.add(0,cn);
                        } else if (cn + width < graphArray.length && graphArray[cn + width] == v - 1) {
                            cn = cn + width;
                            v = v - 1;
                            newPath.add(0,cn);
                        }
                    } while (cn != startIndex);
                    return newPath;
                }
            } else {
                v = graphArray[current];
                if (current + 1 < graphArray.length && current % width != 0 && graphArray[current + 1] == 0) {
                    graphArray[current + 1] = v + 1;
                    agenda.add(0,current + 1);
                }
                if (current - 1 >= 0 && current - 1 % width != 0 && graphArray[current - 1] == 0) {
                    graphArray[current - 1] = v + 1;
                    agenda.add(0,current - 1);
                }
                if (current + width < graphArray.length && graphArray[current + width] == 0) {
                    graphArray[current + width] = v + 1;
                    agenda.add(0,current + width);
                }
                if (current - width >= 0 && graphArray[current - width] == 0) {
                    graphArray[current - width] = v + 1;
                    agenda.add(0,current - width);
                }
            }
        }
        while (!agenda.isEmpty());
        return null;
    }

}

