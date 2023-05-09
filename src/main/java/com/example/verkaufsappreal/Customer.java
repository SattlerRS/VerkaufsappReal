package com.example.verkaufsappreal;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    public int ID;
    public String firstname;
    public String lastname;
    public Date birth;
    public String email;
    public String tel;
    public String passwort;

    public Customer(int ID, String firstname, String lastname, Date birth, String email, String tel, String passwort) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birth = birth;
        this.email = email;
        this.tel = tel;
        this.passwort = passwort;
    }
}
