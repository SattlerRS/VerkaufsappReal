package com.example.verkaufsappreal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class OrdersWinController implements Initializable {

    public MainWin mainWin;
    public OrdersWin ordersWin;

    public ListView<String> listViewOrders;

    public Button btnClose;
    public Button btnShowFilter;
    public Button btnFilter;
    public Label label;
    public Label labelFrom;
    public Label labelTo;
    public DatePicker datePickerFrom;
    public DatePicker datePickerTo;
    public boolean hide;



    public OrdersWinController(MainWin mainWin, OrdersWin ordersWin) {
        this.mainWin = mainWin;
        this.ordersWin = ordersWin;
    }

    @FXML
    public void btnFilter(){
        double total = 0;
        LocalDateTime dateTimeFrom = datePickerFrom.getValue().atStartOfDay();
        LocalDateTime dateTimeTo = datePickerTo.getValue().atTime(23,59,59);
        try {
            Database.orders.clear();
            Database.readOrdersFromCustomer();
            listViewOrders.getItems().clear();
            for (int i=Database.orders.size(); --i >= 0;)
            {
                if(Database.orders.get(i).date.toLocalDateTime().isAfter(dateTimeFrom) && Database.orders.get(i).date.toLocalDateTime().isBefore(dateTimeTo)){
                listViewOrders.getItems().add(Database.orders.get(i).stringListView());
                total += Database.orders.get(i).priceTotal;
                }
            }
            label.setText("Gesamtsumme: " + String.format("%,.2f", total) + " €");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void btnShowFilter(){
        if(hide){
            hide = false;
            hideFilter();
        }
        else{
            hide = true;
            hideFilter();
        }

    }
    @FXML
    public void btnClose(){
        ordersWin.stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hide = true;
        hideFilter();
        setImagesToButton();
        try {
            Database.orders.clear();
            Database.readOrdersFromCustomer();
            updateListView();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateListView(){
        double total = 0;
        listViewOrders.getItems().clear();
        for (int i=Database.orders.size(); --i >= 0;)
        {
            listViewOrders.getItems().add(Database.orders.get(i).stringListView());
            total += Database.orders.get(i).priceTotal;
        }
        datePickerFrom.setValue(Database.orders.get(0).date.toLocalDateTime().toLocalDate());
        datePickerTo.setValue(Database.orders.get(Database.orders.size()-1).date.toLocalDateTime().toLocalDate());
        label.setText("Gesamtsumme: " + String.format("%,.2f", total) + " €");
    }
    public void hideFilter(){
        if (hide){
            btnFilter.setVisible(false);
            datePickerFrom.setVisible(false);
            datePickerTo.setVisible(false);
            labelFrom.setVisible(false);
            labelTo.setVisible(false);
        }
        else{
            btnFilter.setVisible(true);
            datePickerFrom.setVisible(true);
            datePickerTo.setVisible(true);
            labelFrom.setVisible(true);
            labelTo.setVisible(true);
        }

    }
    public void setImagesToButton(){
        Image img = new Image("file:src/Images/back.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        btnClose.setGraphic(view);

        Image img2 = new Image("file:src/Images/filterShow.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(20);
        view2.setPreserveRatio(true);
        btnShowFilter.setGraphic(view2);

        Image img3 = new Image("file:src/Images/doFilter.png");
        ImageView view3 = new ImageView(img3);
        view3.setFitHeight(20);
        view3.setPreserveRatio(true);
        btnFilter.setGraphic(view3);

    }
}
