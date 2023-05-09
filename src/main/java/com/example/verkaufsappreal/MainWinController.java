package com.example.verkaufsappreal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Formattable;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWinController implements Initializable {

    public boolean login = false;
    public Label label;
    public Label labelArtikel;
    public Label labelDetail;
    public Label labelLogin;
    public Label labelAmounttext;
    public Label labelInfo;
    public TableView<Article> tableViewMain;
    public TableColumn<Article,String> priceColumn;
    public TableColumn<Article,String> nameColumn;
    public TextArea textAreaDetail;
    public TextField textFieldPassword;
    public TextField textFieldEmail;
    public TextField textFieldAmount;
    public Button btnLogin;
    public Button btnClose;
    public Button btnAddToCart;

    public Button btnCart;
    public Button btnShowBills;

    public MainWin mainWin;

    public MainWinController(MainWin mainWin) {
        this.mainWin = mainWin;
    }

    @FXML
    public void btnShowBills() throws IOException {
        OrdersWin ordersWin = new OrdersWin(mainWin);

    }
    @FXML
    public void btnCart() throws IOException {

        if(Database.shoppingCart.size()>0){
            CartWin cartWin = new CartWin(mainWin);
        }
    }
    @FXML
    public void btnAddToCart() {

        try{
            int amount = Integer.parseInt(textFieldAmount.getText());
            if (tableViewMain.getSelectionModel().getSelectedItems().size() > 0) {
                if (Integer.parseInt(textFieldAmount.getText()) > 0 && Integer.parseInt(textFieldAmount.getText()) <= Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount) {
                    Article temparticel = Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex());
                    if (Database.shoppingCart.contains(temparticel) && amount <= Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount) {
                        temparticel.amount -= amount;
                        Database.shoppingCart.get(Database.shoppingCart.indexOf(temparticel)).billAmount += amount;
                        System.out.println(Database.shoppingCart.get(Database.shoppingCart.indexOf(temparticel)).billAmount);
                        labelInfo.setTextFill(Color.web("#008800"));
                        labelInfo.setText("Wurde dem Warenkorb hinzugefügt");
                        textFieldAmountPromptTextChange(temparticel.amount);
                    } else if (!Database.shoppingCart.contains(temparticel)) {
                        Database.shoppingCart.add(temparticel);
                        Database.shoppingCart.get(Database.shoppingCart.size() - 1).billAmount = amount;
                        btnCart.setText("(" + Database.shoppingCart.size() + ")");
                        labelInfo.setTextFill(Color.web("#008800"));
                        labelInfo.setText("Wurde dem Warenkorb hinzugefügt");
                        temparticel.amount -= amount;
                        textFieldAmountPromptTextChange(temparticel.amount);
                    } else {
                        labelInfo.setTextFill(Color.web("#ff0000"));
                        labelInfo.setText("Lagerstand überschritten");
                        textFieldAmountPromptTextChange(temparticel.amount);
                    }
                }
                else {
                    labelInfo.setTextFill(Color.web("#ff0000"));
                    labelInfo.setText("Lagerstand überschritten");
                    textFieldAmountPromptTextChange(Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount);
                }
            }
        }
        catch (Exception e){
            labelInfo.setTextFill(Color.web("#ff0000"));
            labelInfo.setText("Falsche Eingabe");
            textFieldAmount.clear();
        }
    }
    public void textFieldAmountPromptTextChange(int amount){
        if (amount > 0){
            textFieldAmount.setPromptText("1 - " + String.valueOf(Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount));
            textAreaDetail.setText("Lagerstand:\t" + Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount + " Stück\n\nBeschreibung:\n"+ Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).description);
            textFieldAmount.clear();
        }
        else {
            textFieldAmount.setPromptText("xxx");
            textAreaDetail.setText("Lagerstand:\t" + Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount + " Stück\n\nBeschreibung:\n"+ Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).description);
            textFieldAmount.clear();
        }
    }
    @FXML
    public void btnLogin() {
        if (!login) {
            if (!Objects.equals(textFieldEmail.getText(), "") && !Objects.equals(textFieldPassword.getText(), "")) {
                login = Database.login(textFieldEmail.getText().toLowerCase(), textFieldPassword.getText());
                if (login) {
                    showLoginItems();
                    labelLogin.setText(Database.customer.firstname + " " + Database.customer.lastname);
                }
                else{
                    textFieldPassword.clear();
                    textFieldEmail.clear();
                }
            }
        }
        else {
            login = false;
            hideLoginItems();
        }
    }

    @FXML
    public void btnClose() {
        mainWin.stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = new Image("file:src/Images/cart.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        btnCart.setGraphic(view);
        Image img2 = new Image("file:src/Images/order.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(15);
        view2.setPreserveRatio(true);
        btnShowBills.setGraphic(view2);
        labelInfo.setWrapText(true);
        textAreaDetail.setWrapText(true);
        hideLoginItems();
        // löschen
        textFieldEmail.setText("mundl@gmail.com");
        textFieldPassword.setText("asQW1234");
        // bis hier
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setSortable(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setSortable(false);
        Database.readArticleFromDataBase();
        updateListView();
    }
    public void updateListView(){
        tableViewMain.setItems(Database.articleList);
        tableViewMain.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Article>(){
            @Override
            public void onChanged(Change<? extends Article> change) {
              textFieldAmount.setPromptText("1 - " + Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount);
              textAreaDetail.setText("Lagerstand:\t" + Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).amount + " Stück\n\nBeschreibung:\n"+ Database.articleList.get(tableViewMain.getSelectionModel().getSelectedIndex()).description);
            }
                });
    }
    public void showLoginItems() {
        Image img = new Image("file:src/Images/logout.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        btnLogin.setGraphic(view);
        btnShowBills.setVisible(true);
        label.setVisible(false);
        labelInfo.setVisible(true);
        labelAmounttext.setVisible(true);
        textFieldAmount.setVisible(true);
        textFieldPassword.setText("");
        textFieldEmail.setText("");
        textFieldPassword.setVisible(false);
        textFieldEmail.setVisible(false);
        labelLogin.setVisible(true);
        btnCart.setText("(" + Database.shoppingCart.size() + ")");
        btnCart.setVisible(true);
        btnAddToCart.setVisible(true);
    }
    public void hideLoginItems() {
        Image img = new Image("file:src/Images/login.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setPreserveRatio(true);
        btnLogin.setGraphic(view);
        btnShowBills.setVisible(false);
        label.setVisible(true);
        labelInfo.setVisible(false);
        labelAmounttext.setVisible(false);
        textFieldAmount.setVisible(false);
        btnCart.setVisible(false);
        btnAddToCart.setVisible(false);
        textFieldPassword.setVisible(true);
        textFieldEmail.setVisible(true);
        labelLogin.setVisible(false);
        labelLogin.setText("");
    }
}