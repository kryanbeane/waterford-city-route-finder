package main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML ImageView iv;
    @FXML private Label contextLabel, lengthLabel;
    @FXML private TextField addPointX, addPointY;
    @FXML RadioButton BFSButt, dijkstraButt, singleRouteButt, multipleRoutesButt;
    private Image rawImage, blackWhiteImage;
    private ArrayList<Integer> nodeCoords = new ArrayList<>(4);
    public static ArrayList<Node<?>> agendaList = new ArrayList<>();
    int limit = 10;

    public void initialize() {
        initializeMap();
        try {
            Node<?>[] nodes = Main.loadLandmarks();
            addSavedLandmarksToMap(nodes);
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
        iv.setImage(rawImage);
        contextLabel.setText("");
        lengthLabel.setText("");
    }

    public void addSavedLandmarksToMap(Node<?>[] nodes) {
        int buttonSize=25;
        int labelOffsetX=30;
        int labelOffsetY=5;

        Button[] points = new Button[nodes.length];
        Label[] pointLabels = new Label[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            points[i] = new Button();
            points[i].getStyleClass().clear();
            points[i].setStyle("-fx-background-radius: 50%; -fx-background-image: url('location.png');");
            points[i].setMinSize(buttonSize, buttonSize);
            points[i].setMaxSize(buttonSize, buttonSize);
            points[i].setPrefSize(buttonSize, buttonSize);
            points[i].setTranslateX(nodes[i].getXCoord());
            points[i].setTranslateY(nodes[i].getYCoord());

            pointLabels[i]=new Label(nodes[i].getNodeName());
            pointLabels[i].setTranslateX(nodes[i].getXCoord() + labelOffsetX);
            pointLabels[i].setTranslateY(nodes[i].getYCoord() + labelOffsetY);

            ((Pane)iv.getParent()).getChildren().add(points[i]);
            ((Pane)iv.getParent()).getChildren().add(pointLabels[i]);
        }
    }

    public void addMarkerOnMap(int x, int y) {
        Node<?> node;
        Button marker = new Button();
        if(limit==0) {
            node = new Node<>("Start", x, y);
            marker.getStyleClass().add("mapStartButton");
        }else {
            node = new Node<>("End", x, y);
            marker.getStyleClass().add("mapEndButton");
        }
        agendaList.add(node);
        marker.setShape(new Circle(5));
        marker.setMinSize(10, 10);
        marker.setMaxSize(10, 10);
        marker.setTranslateX(x-5);
        marker.setTranslateY(y-5);
        ((Pane)iv.getParent()).getChildren().add(marker);
    }

    public void findSelectedPath() {
            int w = (int)blackWhiteImage.getWidth();
            int h = (int)blackWhiteImage.getHeight();
            Node<Color>[] nodes = GraphCreator.createNodesFromImg(blackWhiteImage, w, h);
            List<List<Node<?>>> bfsPaths = new ArrayList<>();
            for (int i=0; i<nodeCoords.size(); i+=2) {
                if (i+2 >= nodeCoords.size())
                    continue;
                Node<Color> src = GraphCreator.getNodeAtMouse(w, nodeCoords.get(i), nodeCoords.get(i + 1), nodes);
                Node<Color> dest = GraphCreator.getNodeAtMouse(w, nodeCoords.get(i + 2), nodeCoords.get(i + 3), nodes);
                Search search = new Search(src, dest);
                if (BFSButt.isSelected())
                    Search.bfs(src, dest);
                else
                    Search.dijkstra(src, dest);
                contextLabel.setText("Generated path from (" + src.getXCoord() + ", " + src.getYCoord() + ") to (" + dest.getXCoord() + ", " + dest.getYCoord() + ")");
                bfsPaths.add(search.getPath());
            }
            List<Node<?>> totalPath = Search.addNodePaths(bfsPaths);
            lengthLabel.setText(GraphCreator.getPathCost(totalPath) + "m");
            iv.setImage(GraphCreator.drawPath(rawImage, totalPath));
    }

    public void mapClicked(MouseEvent me) {
        int x=(int)me.getX();
        int y=(int)me.getY();
        addPointX.setText(String.valueOf((int)me.getX()));
        addPointY.setText(String.valueOf((int)me.getY()));
        if(nodeCoords.size()/2<limit) {
            addMarkerOnMap(x, y);
            nodeCoords.add(x);
            nodeCoords.add(y);
        }
    }

}
