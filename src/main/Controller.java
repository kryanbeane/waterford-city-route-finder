package main;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Controller {

    public double xCoord, yCoord, xSrc, ySrc, xDest, yDest;
    public boolean startPoint;
    public int[] graphArray;
    public ArrayList<Integer> bfsList, dfsList;
    public ImageView imageView;
    public WritableImage blackAndWhite;
    public Pane imagePane, labelPane, landmarkPane;
    public Node src, dest;
    public ComboBox srcCombo, destCombo, visitCombo, avoidCombo, dijkstraCombo;
    public Button bfsbtn, addAvoidBtn, addVisitBtn, clearSelection, dfsBtn, findDijkstra;
    public RadioButton customSrcRadio, customDestRadio, pointerForSrc, pointerForDest;

    public void initialize() {
        Main.createGraphList();
        Image img = new Image("waterfordMap.png", 932, 478, false, false);
        Image bwImg = new Image("BWMap2.png", 932, 478, false, false);
        PixelReader pr = bwImg.getPixelReader();
        blackAndWhite = new WritableImage(pr, 932, 478);
        imageView.setImage(img);
        selectWaypoint();
        try {
            Main.loadLandmarks();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        populateComboBox();
        updateLandmarks();
        findRouteDijkstras();
        addWayPoint();
        setGraphArray();
        breadthFirstSearch();
        addAvoid();
        startPoint = true;
        setClearSelection();
        depthFirstSearch();
        setDijkstrasBtn();
    }

    public void populateComboBox() {
        srcCombo.getItems().clear();
        destCombo.getItems().clear();
        srcCombo.getSelectionModel().clearSelection();
        visitCombo.getItems().clear();
        avoidCombo.getItems().clear();
        srcCombo.getItems().addAll(Main.graphlist);
        destCombo.getItems().addAll(Main.graphlist);
        visitCombo.getItems().addAll(Main.graphlist);
        avoidCombo.getItems().addAll(Main.graphlist);
    }

    public void colorPath(ArrayList<Integer> arrayList) {
        for (int i : arrayList) {
            double x = i % imageView.getImage().getWidth();
            double y = i / imageView.getImage().getWidth() + 1;
            Circle circle = new Circle();
            circle.setLayoutX(x);
            circle.setLayoutY(y);
            circle.setRadius(1);
            circle.setFill(Color.RED);
            imagePane.getChildren().add(circle);
        }
    }

    public void addWayPoint() {
        addVisitBtn.setOnAction(e -> {
            Node<?> waypoint = (Node<?>) visitCombo.getSelectionModel().getSelectedItem();
            Main.waypoints.add(waypoint);
        });
    }

    public void setDijkstrasBtn() {
        ObservableList<Object> dijkstrasOptions = FXCollections.observableArrayList();
        dijkstrasOptions.add("Classic");
        dijkstrasOptions.add("Historical");
        dijkstrasOptions.add("Easiest");
        dijkstraCombo.getItems().addAll(dijkstrasOptions);
    }

    public void findRouteDijkstras() {
        findDijkstra.setOnAction(e -> {
            try {
                CostedPath cp = new CostedPath();
                ArrayList<Integer> dijkstraList = new ArrayList<>();
                Node<?> src = (Node<?>) srcCombo.getSelectionModel().getSelectedItem();
                Node<?> dest = (Node<?>) destCombo.getSelectionModel().getSelectedItem();
                Main.waypoints.add(0, src);
                CostedPath tempCp;
                Main.waypoints.add(dest);
                String modifier;
                if (dijkstraCombo.getSelectionModel().getSelectedItem().equals("Easiest")) modifier = "Easiest";
                else if (dijkstraCombo.getSelectionModel().getSelectedItem().equals("Historical")) modifier = "Historical";
                else modifier = "Classic";
                for (int i = 0; i < Main.waypoints.size() - 1; i++) {
                    tempCp = Search.findCheapestPathDijkstra(Main.waypoints.get(i), Main.waypoints.get(i + 1).data, Main.avoids, modifier);
                    cp.pathCost += tempCp.pathCost;
                    for (int j = 0; j < tempCp.pathList.size(); j++)
                        cp.pathList.add(tempCp.getPathList().get(j));
                }
                for (int i = 0; i < cp.pathList.size() - 1; i++) {
                    int[] arr = createGraphArray(blackAndWhite);
                    if (!(cp.pathList.get(i).equals(cp.pathList.get(i + 1))))
                        dijkstraList.addAll(Search.bfs(cp.pathList.get(i), cp.pathList.get(i + 1), (int)imageView.getImage().getWidth(), arr));
                }
                colorPath(dijkstraList);
                Main.waypoints.clear();
                Main.avoids.clear();
            } catch (Exception exc) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please try again!");
                alert.showAndWait();
            }
        });
    }

    public void setGraphArray() {
        graphArray = createGraphArray(blackAndWhite);
    }

    public void breadthFirstSearch() {
        bfsbtn.setOnAction(e -> {
            try {
                if (pointerForSrc.isSelected()) {
                    src = new Node<>(xSrc, ySrc);
                    src.x = xSrc;
                    src.y = ySrc;
                } else
                    src = (Node<?>) srcCombo.getSelectionModel().getSelectedItem();
                if (pointerForDest.isSelected()) {
                    dest = new Node<>(xDest, yDest);
                    dest.x = xDest;
                    dest.y = yDest;
                } else
                    dest = (Node<?>) destCombo.getSelectionModel().getSelectedItem();
                int[] graphArr = createGraphArray(blackAndWhite);
                bfsList = Search.bfs(src, dest, 932, graphArr);
                colorPath(bfsList);
            } catch (Exception exc) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No valid Start and Destination locations selected");
                alert.showAndWait();
            }
        });
    }

    public void depthFirstSearch() {
        dfsBtn.setOnAction(e -> {
            int width = (int) imageView.getImage().getWidth();
            try {
                if (customSrcRadio.isSelected()) {
                    src = new Node<>(xSrc, ySrc);
                    src.x = xSrc;
                    src.y = ySrc;
                } else
                    src = (Node<?>) srcCombo.getSelectionModel().getSelectedItem();
                if (customDestRadio.isSelected()) {
                    destCombo.setDisable(true);
                    dest = new Node<>(xDest, yDest);
                    dest.x = xDest;
                    dest.y = yDest;
                } else
                    dest = (Node<?>) destCombo.getSelectionModel().getSelectedItem();
                int[] graphArr = createGraphArray(blackAndWhite);
                dfsList = Search.dfs(src, dest, width, graphArr);
                colorPath(dfsList);
            } catch (Exception exc) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Choose a new source and destination!");
                alert.showAndWait();
            }
        });
    }

    public int[] createGraphArray(WritableImage im) {
        PixelReader pr = im.getPixelReader();
        int[] graphArr = new int[932*478];
        for (int y=0; y<478; y++) {
            for (int x = 0; x<932; x++) {
                int currPixel = (y*932 + x);
                Color color = pr.getColor(x, y);
                if (color.equals(Color.valueOf("0xffffffff"))) graphArr[currPixel] = 0;
                else graphArr[currPixel] = -1;
            }
        }
        return graphArr;
    }

    public void checkPointSuitability(double x, double y, int selection) {
        if (graphArray[((int)(y*imageView.getImage().getWidth() + x))]==0) {
            Circle circle = new Circle();
            circle.setLayoutX(x);
            circle.setLayoutY(y);
            circle.setRadius(6);
            if (selection == 3) {
                try {
                    imagePane.getChildren().removeIf(node -> node.getId().equals("Landmark"));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                circle.setId("Landmark");
                circle.setFill(Color.PURPLE);
            } else if (selection == 1) {
                try {
                    imagePane.getChildren().removeIf(node -> node.getId().equals("Start"));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                circle.setId("Start");
                circle.setFill(Color.GREEN);
            } else {
                try {
                    imagePane.getChildren().removeIf(node -> node.getId().equals("Destination"));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                circle.setId("Destination");
                circle.setFill(Color.RED);
            }
            imagePane.getChildren().add(circle);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Landmark Error");
            alert.setContentText("Please select a road next to a new location");
            alert.showAndWait();
        }
    }

    public void selectWaypoint() {
        int selectStart = 1;
        int selectDest = 2;
        int selectLandmk = 3;
        landmarkPane.setOnMouseClicked(e -> {
            if (customSrcRadio.isSelected()) {
                xSrc = e.getX();
                ySrc = e.getY();
                checkPointSuitability(xSrc, ySrc, selectStart);
                customSrcRadio.setSelected(false);
            } else if (customDestRadio.isSelected()) {
                xDest = e.getX();
                yDest = e.getY();
                checkPointSuitability(xDest, yDest, selectDest);
                customDestRadio.setSelected(false);
            } else {
                xCoord = e.getX();
                yCoord = e.getY();
                checkPointSuitability(xCoord, yCoord, selectLandmk);
            }
        });
    }

    public void addAvoid() {
        addAvoidBtn.setOnAction(e -> {
            Node<?> avoid = (Node<?>) avoidCombo.getSelectionModel().getSelectedItem();
            Main.avoids.add(avoid);
        });
    }

    public void updateLandmarks() {
        imagePane.getChildren().clear();
        landmarkPane.getChildren().clear();
        srcCombo.getItems().clear();
        destCombo.getItems().clear();
        visitCombo.getItems().clear();
        avoidCombo.getItems().clear();
        for (Object node : Main.graphlist) {
            Circle circle = new Circle();
            circle.setLayoutX(((Landmark) (((Node<?>) node).data)).x);
            circle.setLayoutY(((Landmark) (((Node<?>) node).data)).y);
            circle.setRadius(6);
            circle.setFill(Color.ORANGE);
            Tooltip tooltip = new Tooltip(((Landmark) (((Node<?>) node).data)).landmarkName);
            Tooltip.install(circle, tooltip);
            landmarkPane.getChildren().add(circle);
            srcCombo.getItems().add(node);
            destCombo.getItems().add(node);
            visitCombo.getItems().add(node);
            avoidCombo.getItems().add(node);
        }
    }

    public void setClearSelection() {
        clearSelection.setOnAction(e -> {
            imagePane.getChildren().clear();
            Main.waypoints.clear();
            Main.avoids.clear();
            updateLandmarks();
            populateComboBox();
        });
    }
}
