package com.example.verkaufsappreal;

import java.sql.Date;
import java.time.LocalDateTime;

public class Order
{
    public int billNumber;

    public int artikelID;
    public java.sql.Timestamp date;
    public int billAmount;
    public String name;
    public Double pricePiece;
    public Double priceTotal;

    public Order(int billNumber, int artikelID, java.sql.Timestamp date, int billAmount, String name, Double pricePiece, Double priceTotal) {
        this.billNumber = billNumber;
        this.artikelID = artikelID;
        this.date = date;
        this.billAmount = billAmount;
        this.name = name;
        this.pricePiece = pricePiece;
        this.priceTotal = priceTotal;
    }
    public String stringListView(){
        return "BestellNr.:\t\t" + billNumber + "\t\t\t Datum:\t\t\t" + date + "\nArtikelNr.:\t\t\t" + artikelID + "\t\t\t Bezeichnung:\t\t" + name + "\nBestellmenge:\t\t" + billAmount + "\t\t\t Preis/Stk.:\t\t" + String.format("%,.2f", pricePiece) + " €\nPreis total:\t\t" + String.format("%,.2f", priceTotal) +" €\n";
    }
}
