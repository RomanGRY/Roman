package com.wvapp.webviewapp.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_table")
public class Person {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String phoneNumber;

    public Person() {}

    public Person(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
