package com.example.verkaufsappreal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CartWin {

    public Stage stage;
    public Scene scene;
    public CartWinController controller;
    public MainWin mainWin;

    public CartWin(MainWin mainWin) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("CartWin.fxml"));
        this.controller = new CartWinController(mainWin,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 285, 469);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Warenkorb");
        this.stage.getIcons().add(new Image("file:src/Images/cart.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }
}
