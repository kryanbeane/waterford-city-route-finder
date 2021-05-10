package main;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GraphCreator {

    public static Node<Color>[] createNodesFromImg(Image img, int w, int h) {
        Node<Color>[] nodes = new Node[(int)(w*h)];
        PixelReader pixelReader = img.getPixelReader();
        for (int x=0; x<h; x++)
            for (int z=0; z<w; z++) {
                if(pixelReader.getColor(z, x).equals(Color.BLACK))
                    continue;
                Node<Color> node = new Node<>("PATH@" +z+ ":" +x, z, x);
                node.setData(pixelReader.getColor(node.getXCoord(), node.getYCoord()));
                nodes[(x*w)+z] = node;
            }
        return linkNodes(w, nodes);
    }

    public static Node<Color>[] linkNodes(int w, Node<Color>[] nodes) {
        for (int i=0; i<nodes.length; i++) {
            if (nodes[i] == null)
                continue;
            if ((i+1)%w != 0)
                if (i+1 < nodes.length)
                    if (nodes[i+1] != null)
                        nodes[i].connectToNodeUndirected(nodes[i+1], 1);

            if (!(i+w >= nodes.length))
                if (nodes[i+w] != null)
                    nodes[i].connectToNodeUndirected(nodes[i+w], 1);
        }
        return nodes;
    }

    public static int getPathCost(List<Node<?>> path) {
        if(path.size()<=1) return 0;
        int cost=0;
        for(int i=0; i<path.size()-1; i++) {
            Node<?> currentNode = path.get(i);
            Node<?> nextNode = path.get(i + 1);
            for(Link adjEdge : currentNode.getAdjList())
                if(adjEdge.getDestNode().equals(nextNode))
                    cost+=adjEdge.getCost();
        }
        return cost;
    }

    public static Image drawPath(Image img, List<Node<?>> nodePath) {
        WritableImage wi = new WritableImage(img.getPixelReader() ,(int)img.getWidth(), (int)img.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(Node<?> node : nodePath)
            pw.setColor(node.getXCoord(), node.getYCoord(), Color.BLUE);
        return wi;
    }

    public static Node<Color> getNodeAtMouse(int width, int x, int y, Node<Color>[] nodes) {
        return nodes[(y*width)+x];
    }

}
