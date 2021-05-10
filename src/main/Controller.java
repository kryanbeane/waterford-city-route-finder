package main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    ImageView iv;
    Image rawImage, blackWhiteImage;
    ArrayList<Integer> nodeCoords = new ArrayList<>(4);
    ArrayList<Button> markers = new ArrayList<>(2);
    ArrayList<Node<?>> pathNodes, agendaList = new ArrayList<>();
    Node<?> src = null, dest = null;
    int limit = 10;

    public void initialize() {
        initializeMap();
        try {
            Node<?>[] nodes = Main.loadLandmarks();
        } catch (Exception e) {
            System.out.println(e);
        }
        rawImage = iv.getImage();
    }

    public void initializeMap() {
        blackWhiteImage = new Image("BlackWhiteMap.png", 1118, 561, false, true);
        iv.setImage(new Image("MapOfWaterford.png", 1118, 561, false, true));
    }

    public void resetMap() {
        nodeCoords = new ArrayList<>(8);
        src = null;
        dest = null;
        for (Button but : markers)
            ((Pane) iv.getParent()).getChildren().remove(but);
        markers.clear();
    }

    public void addSavedLandmarksToMap(Node<?>[] nodes) {
        int buttonSize = 5;
        int labelOffsetX = 35;
        int labelOffsetY = 8;

        Button[] landmarks = new Button[nodes.length];
        Label[] pointLabels = new Label[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            landmarks[i] = new Button();
            landmarks[i].getStyleClass().clear();
            landmarks[i].setStyle("-fx-background-radius: 50%; -fx-background-image: url('location.png');");
            landmarks[i].setMinSize(buttonSize, buttonSize);
            landmarks[i].setMaxSize(buttonSize, buttonSize);
            landmarks[i].setPrefSize(buttonSize, buttonSize);
            landmarks[i].setTranslateX(nodes[i].getXCoord());
            landmarks[i].setTranslateY(nodes[i].getYCoord());

            pointLabels[i] = new Label(nodes[i].getNodeName());
            pointLabels[i].setTranslateX(nodes[i].getXCoord() + labelOffsetX);
            pointLabels[i].setTranslateY(nodes[i].getYCoord() + labelOffsetY);

            ((StackPane) iv.getParent()).getChildren().add(landmarks[i]);
            ((StackPane) iv.getParent()).getChildren().add(pointLabels[i]);
        }
    }

    public void addMarkerOnMap(int x, int y) {
        PixelReader pr = blackWhiteImage.getPixelReader();
        if (pr.getColor(x, y) != Color.valueOf("0x000000ff")) {
            Node<?> temp = new Node<>(limit == 0 ? "Source" : "Destination", x, y);
            agendaList.add(temp);
            Button marker = new Button();
            marker.getStyleClass().add(limit == 0 ? "mapStartBtn" : "mapEndBtn");
            double r = 5;
            marker.setShape(new Circle(r));
            marker.setStyle("-fx-background-radius: 50%; -fx-background-image: url('location.png');");
            marker.setMinSize(5, 5);
            marker.setMaxSize(5, 5);
            marker.setTranslateX(x - 2);
            marker.setTranslateY(y - 2);
            ((Pane) iv.getParent()).getChildren().add(marker);
            markers.add(marker);
        }
    }

    public void findCheapestDijkstraPath() {
        createNodesOfPixels();
        CostedPath cp = Search.dijkstra(src, dest);
        iv.setImage(drawPath(iv.getImage(), cp.pathList));
    }

    public void findCheapestBFSPath() {
        createNodesOfPixels();
        List<Node<?>> bfsPath = Search.bfs(src, dest);
        iv.setImage(drawPath(iv.getImage(), bfsPath));
    }

    public void selectPoints(MouseEvent me) {
        PixelReader pr = blackWhiteImage.getPixelReader();
        int x = (int) me.getX();
        int y = (int) me.getY();
        if (pr.getColor(x, y) != Color.valueOf("0x000000ff")) {
            if (src != null && dest != null) {
                resetMap();
                selectPoints(me);
            } else if (src == null) {
                src = new Node<>("Source", x, y);
                addMarkerOnMap(x, y);
            } else {
                dest = new Node<>("Destination", x, y);
                addMarkerOnMap(x, y);
            }
        }
    }

    public void createNodesOfPixels() {
        pathNodes = new ArrayList<>();
        int w = (int) blackWhiteImage.getWidth();
        int h = (int) blackWhiteImage.getHeight();
        PixelReader pr = blackWhiteImage.getPixelReader();
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                Color color = pr.getColor(j, i);
                if (color.equals(Color.WHITE))
                    pathNodes.add(new Node<>(i));
            }

        for (int i = 0; i < pathNodes.size(); i++)
            if (pathNodes.get(i) != null) {
                if ((i - w) >= 0 && pathNodes.get(i-w) != null) {
                    pathNodes.get(i).connectToNodeDirectedDijkstra(pathNodes.get(i-w), 1);
                    if ((i + w) < pathNodes.size() && pathNodes.get(i+w) != null)
                        pathNodes.get(i).connectToNodeDirectedDijkstra(pathNodes.get(i+w), 1);
                    if ((i - 1) >= 0 && pathNodes.get(i-1) != null && ((i - 1) % w) != 0)
                        pathNodes.get(i).connectToNodeDirectedDijkstra(pathNodes.get(i-1), 1);
                    if ((i + 1) < pathNodes.size() && pathNodes.get(i+1) != null && ((i + 1) % w) != 0)
                        pathNodes.get(i).connectToNodeDirectedDijkstra(pathNodes.get(i+1), 1);
                }
            }
    }

    public static Image drawPath(Image img, List<Node<?>> nodePath) {
        WritableImage wi = new WritableImage(img.getPixelReader() ,(int)img.getWidth(), (int)img.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(Node<?> node : nodePath)
            pw.setColor(node.getXCoord(), node.getYCoord(), Color.BLUE);
        return wi;
    }
}
