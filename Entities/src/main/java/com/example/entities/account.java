package com.example.entities;

import java.io.Serializable;

public class account implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String password;

    public account(int id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
