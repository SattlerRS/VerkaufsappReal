package com.example.verkaufsappreal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OrdersWin {
    public Stage stage;
    public Scene scene;
    public OrdersWinController controller;
    public MainWin mainWin;

    public OrdersWin(MainWin mainWin) throws IOException {
        this.mainWin = mainWin;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWin.class.getResource("OrdersWin.fxml"));
        this.controller = new OrdersWinController(mainWin,this);
        fxmlLoader.setController(controller);
        this.scene = new Scene(fxmlLoader.load(), 483, 541);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Bestellungen");
        this.stage.getIcons().add(new Image("file:src/Images/order.png"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }
}
