package main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/routeFinder.fxml")));
        primaryStage.setTitle("Waterford City Route Finder");
        primaryStage.setScene(new Scene(root, 750, 500));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            saveLandmarks(getLandmarks());
        } catch (Exception e) {
            System.out.println(e);
        }
        launch(args);
    }

    public static Node<String>[] loadLandmarks() throws IOException, ClassNotFoundException {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/resources/landmarks.xml"));
        Node<String>[] nodes = (Node<String>[]) is.readObject();
        is.close();

        return nodes;
    }

    public static void saveLandmarks(Node<String>[] nodes) throws Exception {
        XStream xstream=new XStream(new DomDriver());
        ObjectOutputStream out=xstream.createObjectOutputStream(new FileWriter("src/resources/landmarks.xml"));
        out.writeObject(nodes);
        out.close();
    }

    public static Node<String>[] getLandmarks() {
        Node<String> waterfordHospital=new Node<>("Waterford Hospital", "Waterford Hospital", 550, 285);
        Node<String> clockTower=new Node<>("Clock Tower", "Clock Tower", 347, 128);
        Node<String> peoplesPark=new Node<>("Peoples Park", "Peoples Park", 385, 182);
        Node<String> kilbarryNaturePark=new Node<>("Kilbarry Nature Park", "Kilbarry Nature Park", 300, 323);
        Node<String> mayParkTrail=new Node<>("May Park Trail", "May Park Trail", 432, 245);
        Node<String> reginaldsTower=new Node<>("Reginalds Tower", "Reginalds Tower", 392, 146);
        Node<String> waterfordMedievalMuseum=new Node<>("Waterford Medieval Museum", "Waterford Medieval Museum", 378, 164);
        Node<String> houseOfWaterfordCrystal=new Node<>("House of Waterford Crystal", "House of Waterford Crystal", 382, 159);

        Node<String>[] landmarks=new Node[8];

        landmarks[0]=waterfordHospital;
        landmarks[1]=clockTower;
        landmarks[2]=peoplesPark;
        landmarks[3]=kilbarryNaturePark;
        landmarks[4]=mayParkTrail;
        landmarks[5]=reginaldsTower;
        landmarks[6]=waterfordMedievalMuseum;
        landmarks[7]=houseOfWaterfordCrystal;

        waterfordHospital.connectToNodeUndirected(waterfordHospital, 10);
        clockTower.connectToNodeUndirected(clockTower, 10);
        peoplesPark.connectToNodeUndirected(peoplesPark, 10);
        kilbarryNaturePark.connectToNodeUndirected(kilbarryNaturePark, 10);
        mayParkTrail.connectToNodeUndirected(mayParkTrail, 10);
        reginaldsTower.connectToNodeUndirected(reginaldsTower, 10);
        waterfordMedievalMuseum.connectToNodeUndirected(waterfordMedievalMuseum, 10);
        houseOfWaterfordCrystal.connectToNodeUndirected(houseOfWaterfordCrystal, 10);
        return landmarks;
    }

}
