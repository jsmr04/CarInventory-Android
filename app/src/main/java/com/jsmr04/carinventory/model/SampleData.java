package com.jsmr04.carinventory.model;

import android.content.Context;

public class SampleData {
    public void insertUsers(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        User user1 = new User();
        user1.setUserName("john_connor@car.com");
        user1.setPassword("1234");
        user1.setName("John Connor");
        user1.setType(User.USER_TYPE_SIMPLE);

        User user2 = new User();
        user2.setUserName("peter_park@car.com");
        user2.setPassword("1234");
        user2.setName("Peter Park");
        user2.setType(User.USER_TYPE_MANAGER);

        try {
            databaseHelper.insertUser(user1);
            databaseHelper.insertUser(user2);
        }catch (android.database.SQLException e){
            e.printStackTrace();
        }
    }

    public void insertCars(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        Car car1 = new Car();
        car1.setName("Brand new Honda Civic");
        car1.setModel("Honda Civic");
        car1.setYear(2020);
        car1.setColor("White");
        car1.setPrice(30000f);
        car1.setVin("UUDDHHJJDDNNEDHWE");
        car1.setImage("");

        Car car2 = new Car();
        car2.setName("Used Toyota Corolla");
        car2.setModel("Toyota Corolla");
        car2.setYear(2010);
        car2.setColor("Blue");
        car2.setPrice(10000f);
        car2.setVin("IKDLDJFHF88D");
        car2.setImage("");

        try {
            databaseHelper.insertCar(car1);
            databaseHelper.insertCar(car2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
