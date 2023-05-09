package com.example.verkaufsappreal;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Database
{

    public static ObservableList<Article> articleList = FXCollections.observableArrayList();

    public static ArrayList<Article> shoppingCart = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();
    public static Customer customer;

    public static String url = "jdbc:mysql://localhost:3306/verkaufsapp";
    public static  String user = "root";
    public static String pass = "";
    public static void readArticleFromDataBase(){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM artikel");
            while(rs.next()){
                Database.articleList.add(new Article(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4),rs.getString(5)));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static boolean login(String email, String password){
        int i = 0;
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM customers WHERE email='" + email+ "' AND password='" + password +"'");
            while(rs.next()){
                i++;
                customer = new Customer(rs.getInt("ID"),rs.getString("firstname"),rs.getString("lastname"),rs.getDate("birth"),rs.getString("email"),rs.getString("tel"),rs.getString("password"));
            }
            con.close();
            return i > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void changeDataFromCartWin(){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            for (Article article : Database.shoppingCart){
                String update = "UPDATE artikel SET amount ='" + article.amount +"' WHERE name='" + article.name + "'";
                stm.execute(update);
                String insert = "INSERT INTO bestellung(customerID, artikelID,date,billAmount) VALUES ('"+Database.customer.ID+"','"+article.ID+"','"+ LocalDateTime.now() +"','"+article.billAmount+"')";
                stm.execute(insert);
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readOrdersFromCustomer() throws SQLException {
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM bestellung INNER JOIN artikel ON bestellung.artikelID = artikel.ID WHERE customerID='"+Database.customer.ID+"'");
            while(rs.next()){
                Database.orders.add(new Order(rs.getInt("billNumber"),rs.getInt("artikelID"),rs.getTimestamp("date"),rs.getInt("billAmount"),rs.getString("name"),rs.getDouble("price"),rs.getDouble("price")*rs.getInt("billAmount")));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
