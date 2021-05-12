package main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    public static ObservableList<Landmark> landmarks;
    public static ArrayList<Node> graphlist;
    public static ArrayList<Node> waypoints;
    public static ArrayList<Node> avoids;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui.fxml")));
        primaryStage.setScene(new Scene(root, 1220, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        saveLandmarks(getLandmarks());
        launch();
    }

    public static void createGraphList() {
        graphlist = new ArrayList<>();
        waypoints = new ArrayList<>();
        avoids = new ArrayList<>();
        landmarks = FXCollections.observableArrayList();
    }

    public static void saveLandmarks(ArrayList<Node> nodes) throws Exception {
        XStream xstream=new XStream(new DomDriver());
        ObjectOutputStream out=xstream.createObjectOutputStream(new FileWriter("src/resource/landmarks.xml"));
        out.writeObject(nodes);
        out.close();
    }

    public static ArrayList<Node> getLandmarks() {
        ArrayList<Node> lms = new ArrayList<>();

        Landmark l1 = new Landmark( 572, 211, "Reginald's Tower");
        Node n1 = new Node(l1);
        lms.add(n1);
        Landmark l2 = new Landmark( 558, 284, "People's Park");
        Node n2 = new Node(l2);
        lms.add(n2);
        Landmark l3 = new Landmark(523, 191, "The Clock Tower");
        Node n3 = new Node(l3);
        lms.add(n3);
        Landmark l4 = new Landmark(461, 146, "Edmund Rice Bridge");
        Node n4 = new Node(l4);
        lms.add(n4);
        Landmark l5 = new Landmark(472, 220, "Ballybricken");
        Node n5 = new Node(l5);
        lms.add(n5);
        Landmark l6 = new Landmark(561, 217, "Ballybricken");
        Node n6 = new Node(l6);
        lms.add(n6);
        Landmark l7 = new Landmark( 553, 233, "House of Waterford Crystal");
        Node n7 = new Node(l7);
        lms.add(n7);
        Landmark l8 = new Landmark(807, 354, "May Park Trail");
        Node n8 = new Node(l8);
        lms.add(n8);
        Landmark l9 = new Landmark(393, 406, "RSC Waterford");
        Node n9 = new Node(l9);
        lms.add(n9);
        Landmark l10 = new Landmark(279, 436, "Waterford Institute of Technology");
        Node n10 = new Node(l10);
        lms.add(n10);
        Landmark l11 = new Landmark( 798, 407, "University Hospital Waterford");
        Node n11 = new Node(l11);
        lms.add(n11);

        return lms;
    }

    public static void loadLandmarks() throws IOException, ClassNotFoundException {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/resource/landmarks.xml"));
        graphlist = (ArrayList<Node>)is.readObject();
        is.close();
    }
}