package com.example.verkaufsappreal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CartWinController implements Initializable {

    public MainWin mainWin;
    public CartWin cartWin;
    public ListView<String> listViewCart;
    public Button btnOrder;
    public Button btnCancel;
    public ComboBox<Integer> comboBoxAmount;
    public Button btnChangeAmount;

    public Button btnDelCartItem;
    public Label labelSumme;

    public CartWinController(MainWin mainWin, CartWin cartWin) {
        this.mainWin = mainWin;
        this.cartWin = cartWin;
    }

    @FXML
    public void btnChangeAmount(){
        if (listViewCart.getSelectionModel().getSelectedItems().size() > 0 && comboBoxAmount.getSelectionModel().getSelectedItem() >0 && comboBoxAmount.getSelectionModel().getSelectedItem() !=  Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount) {
            int fullAmount = Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).amount + Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount;
            Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount = comboBoxAmount.getSelectionModel().getSelectedItem();
            Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).amount = fullAmount -comboBoxAmount.getSelectionModel().getSelectedItem();
            updateListView();
            mainWin.controller.textFieldAmountPromptTextChange(1);
            updateSumme();
        }
    }
    @FXML
    public void btnDelCartItem(){
        if (listViewCart.getSelectionModel().getSelectedItems().size() > 0) {
            Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).amount += Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount;
            Database.shoppingCart.remove(listViewCart.getSelectionModel().getSelectedIndex());
            updateListView();
            mainWin.controller.textFieldAmountPromptTextChange(1);
            mainWin.controller.btnCart.setText("(" + Database.shoppingCart.size() + ")");
            updateSumme();

        }
    }

    @FXML
    public void btnOrder() {
        if(Database.shoppingCart.size()>0){
            Database.changeDataFromCartWin();
            Database.shoppingCart.clear();
            mainWin.controller.btnCart.setText("(" + Database.shoppingCart.size() + ")");
            cartWin.stage.close();
        }

    }
    @FXML
    public void btnCancel() {
        cartWin.stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = new Image("file:src/Images/del.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        btnDelCartItem.setGraphic(view);
        Image img2 = new Image("file:src/Images/refresh.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(15);
        view2.setPreserveRatio(true);
        updateSumme();
        btnChangeAmount.setGraphic(view2);
        updateListView();
        listViewCart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                comboBoxAmount.getItems().clear();
                comboBoxAmount.setPromptText(String.valueOf(Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount));
                for(int i=0;i<Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).amount+Database.shoppingCart.get(listViewCart.getSelectionModel().getSelectedIndex()).billAmount;i++ ){
                    comboBoxAmount.getItems().add(i,i+1);
                }
            }
        });
    }
    public void updateListView(){
        listViewCart.getItems().clear();
        for (Article article : Database.shoppingCart) {
            listViewCart.getItems().add(article.stringListViewCart());
        }
    }
    public void updateSumme(){
        double sum = 0;
        for(Article article : Database.shoppingCart){
            sum += article.price*article.billAmount;
        }
        labelSumme.setText("Gesamtsumme:\t" + String.format("%,.2f", sum) + " €");
    }
}
