package com.example.verkaufsappreal;

public class Article
{
    public int ID;
    public String name;
    public int amount;
    public double price;
    public String description;
    public int billAmount = 0;

    public Article(int ID, String name, int amount, double price, String description) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return String.format("%,.2f €", price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public String stringListView(){
        return "Bezeichnug:\t" + name + "\nPreis: \t\t" + String.format("%,.2f", price) + " €";
    }
    public String stringListViewCart(){
        return "Bezeichnug:\t\t" + name + "\nPreis/Stk: \t\t\t" + String.format("%,.2f", price) + " €\nAnzahl: \t\t\t" + billAmount + " Stück\nPreis für " + billAmount +" Stk:  \t" + String.format("%,.2f", price*billAmount) + " Euro";
    }
}
