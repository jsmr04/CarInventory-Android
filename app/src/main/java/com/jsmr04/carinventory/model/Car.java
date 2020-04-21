package com.jsmr04.carinventory.model;

import java.io.Serializable;

public class Car implements Serializable {
    public static final String TABLE_NAME = "car";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_MODEL = "model";
    public static final String COLUMN_NAME_YEAR = "year";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_COLOR = "color";
    public static final String COLUMN_NAME_VIN = "vin";
    public static final String COLUMN_NAME_IMAGE = "image";

    private String name;
    private String model;
    private int year;
    private Float price;
    private String color;
    private String vin;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", vin='" + vin + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
