package com.example.verkaufsappreal;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWin {

    public Stage stage;
    public Scene scene;
    public MainWinController controller;

    public MainWin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("MainWin.fxml"));
        this.controller = new MainWinController(this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 471, 429);
        this.stage = new Stage();
        this.stage.setTitle("Verkaufsshop");
        this.stage.getIcons().add(new Image("file:src/Images/shop.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }


}
