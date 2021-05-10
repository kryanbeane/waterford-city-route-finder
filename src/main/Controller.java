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
    @FXML ImageView iv;
    @FXML Label contextLabel, lengthLabel;
    Image rawImage, blackWhiteImage;
    ArrayList<Integer> nodeCoords = new ArrayList<>(4);
    public static ArrayList<Node<?>> agendaList = new ArrayList<>();
    Node<?> src=null, dest=null;
    int limit = 10;
    ArrayList<Button> markers = new ArrayList<>(2);

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
        blackWhiteImage=new Image("BlackWhiteMap.png", (int)iv.getFitWidth(), (int)iv.getFitHeight(), false, true);
        iv.setImage(new Image("MapOfWaterford.png", (int)iv.getFitWidth(), (int)iv.getFitHeight(), false, true));
    }

    public void resetMap() {
        nodeCoords = new ArrayList<>(8);
        src=null;
        dest=null;
        for(Button but : markers)
            ((Pane)iv.getParent()).getChildren().remove(but);
        markers.clear();
    }

    public void addSavedLandmarksToMap(Node<?>[] nodes) {
        int buttonSize=5;
        int labelOffsetX=35;
        int labelOffsetY=8;

        Button[] landmarks = new Button[nodes.length];
        Label[] pointLabels = new Label[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            landmarks[i] = new Button();
            landmarks[i].getStyleClass().clear();
            landmarks[i].setStyle("-fx-background-radius: 50%; -fx-background-image: url('location.png');");
            landmarks[i].setMinSize(buttonSize, buttonSize);
            landmarks[i].setMaxSize(buttonSize, buttonSize);
            landmarks[i].setPrefSize(buttonSize, buttonSize);
            landmarks[i].setTranslateX(nodes[i].getXCoord());
            landmarks[i].setTranslateY(nodes[i].getYCoord());

            pointLabels[i] = new Label(nodes[i].getNodeName());
            pointLabels[i].setTranslateX(nodes[i].getXCoord() + labelOffsetX );
            pointLabels[i].setTranslateY(nodes[i].getYCoord() + labelOffsetY);

            ((StackPane)iv.getParent()).getChildren().add(landmarks[i]);
            ((StackPane)iv.getParent()).getChildren().add(pointLabels[i]);
        }
    }

    public void addMarkerOnMap(int x, int y) {
        PixelReader pr = blackWhiteImage.getPixelReader();
        if(pr.getColor(x, y)!= Color.valueOf("0x000000ff")) {
            Node<?> temp = new Node<>(limit==0 ? "Source" : "Destination", x, y);
            agendaList.add(temp);
            Button marker = new Button();
            marker.getStyleClass().add(limit==0 ? "mapStartBtn" : "mapEndBtn");
            double r = 5;
            marker.setShape(new Circle(r));
            marker.setStyle("-fx-background-radius: 50%; -fx-background-image: url('location.png');");
            marker.setMinSize(5, 5);
            marker.setMaxSize(5, 5);
            marker.setTranslateX(x-2);
            marker.setTranslateY(y-2);
            ((Pane) iv.getParent()).getChildren().add(marker);
            markers.add(marker);
        }
    }

//    public void findSelectedPath() {
//            int w = (int)blackWhiteImage.getWidth();
//            int h = (int)blackWhiteImage.getHeight();
//            Node<Color>[] nodes = GraphCreator.createNodesFromImg(blackWhiteImage, w, h);
//            List<List<Node<?>>> bfsPaths = new ArrayList<>();
//            for (int i=0; i<nodeCoords.size(); i+=2) {
//                if (i+2 >= nodeCoords.size())
//                    continue;
//                Node<Color> src = GraphCreator.getNodeAtMouse(w, nodeCoords.get(i), nodeCoords.get(i + 1), nodes);
//                Node<Color> dest = GraphCreator.getNodeAtMouse(w, nodeCoords.get(i + 2), nodeCoords.get(i + 3), nodes);
//                Search search = new Search(src, dest);
//                if (BFSButt.isSelected())
//                    Search.bfs(src, dest);
//                else
//                    Search.dijkstra(src, dest);
//                contextLabel.setText("Generated path from (" + src.getXCoord() + ", " + src.getYCoord() + ") to (" + dest.getXCoord() + ", " + dest.getYCoord() + ")");
//                bfsPaths.add(search.getPath());
//            }
//            List<Node<?>> totalPath = Search.addNodePaths(bfsPaths);
//            lengthLabel.setText(GraphCreator.getPathCost(totalPath) + "m");
//            iv.setImage(GraphCreator.drawPath(rawImage, totalPath));
//    }

    public void findCheapestDijkstraPath() {
        CostedPath cp = Search.dijkstra(src, dest);
        iv.setImage(GraphCreator.drawPath(iv.getImage(), cp.pathList));
    }

    public void findCheapestBFSPath() {
        List<Node<?>> bfsPath = Search.bfs(src, dest);
        iv.setImage(GraphCreator.drawPath(iv.getImage(), bfsPath));
    }

    public void selectPoints(MouseEvent me) {
        PixelReader pr = blackWhiteImage.getPixelReader();
        int x=(int)me.getX();
        int y=(int)me.getY();
        if(pr.getColor(x, y)!= Color.valueOf("0x000000ff")) {
            if (src != null && dest != null) {
                resetMap();
                selectPoints(me);
            } else if (src == null) {
                src = new Node<>("Source", x, y);
                addMarkerOnMap(x, y);
            }
            else {
                dest = new Node<>("Destination", x, y);
                addMarkerOnMap(x, y);
            }
        }
    }


}
