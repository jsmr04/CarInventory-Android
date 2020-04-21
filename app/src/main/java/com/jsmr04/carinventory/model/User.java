package com.jsmr04.carinventory.model;

import java.io.Serializable;

public class User implements Serializable {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME_USERNAME = "userName";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_TYPE = "type";

    public static final String USER_TYPE_MANAGER = "MANAGER";
    public static final String USER_TYPE_SIMPLE = "SIMPLE";

    private String userName;
    private String password;
    private String name;
    private String type;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
