package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopApp extends Application {
    public static Socket socket;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    @Override
    public void start(Stage primaryStage) throws Exception{
        socket = new Socket("127.0.0.1", 1999);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


        Parent root = FXMLLoader.load(getClass().getResource("Beam_Shop.fxml"));
        primaryStage.setTitle("Beam");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Request req = new Request("BYE");
                try {
                    oos.writeObject(req);
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
