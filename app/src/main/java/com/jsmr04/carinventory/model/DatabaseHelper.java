package com.jsmr04.carinventory.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CarInventory.db";
    public static final int VERSION = 4;
    String TAG = "CarInventory";

    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE "+ User.TABLE_NAME + " ("
                                                        + User.COLUMN_NAME_USERNAME  + " TEXT PRIMARY KEY,"
                                                        + User.COLUMN_NAME_PASSWORD  + " TEXT, "
                                                        + User.COLUMN_NAME_NAME + " TEXT,"
                                                        + User.COLUMN_NAME_TYPE + " TEXT);";

    private static final String SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS "+ User.TABLE_NAME +";";

    private static final  String SQL_CREATE_TABLE_CAR = "CREATE TABLE " + Car.TABLE_NAME + "("
                                                        + Car.COLUMN_NAME_NAME + " TEXT PRIMARY KEY, "
                                                        + Car.COLUMN_NAME_MODEL + " TEXT, "
                                                        + Car.COLUMN_NAME_YEAR + " INTEGER, "
                                                        + Car.COLUMN_NAME_PRICE + " NUMERIC, "
                                                        + Car.COLUMN_NAME_COLOR + " TEXT, "
                                                        + Car.COLUMN_NAME_VIN + " TEXT, "
                                                        + Car.COLUMN_NAME_IMAGE + " TEXT ) ";
    private static final String SQL_DROP_TABLE_CAR = "DROP TABLE IF EXISTS "+ Car.TABLE_NAME +";";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_CAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_USER);
        db.execSQL(SQL_DROP_TABLE_CAR);
        onCreate(db);
    }

    public void insertUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_NAME_USERNAME, user.getUserName());
        contentValues.put(User.COLUMN_NAME_PASSWORD, user.getPassword());
        contentValues.put(User.COLUMN_NAME_NAME, user.getName());
        contentValues.put(User.COLUMN_NAME_TYPE, user.getType());

        this.getWritableDatabase().insertOrThrow(User.TABLE_NAME, "", contentValues);
    }

    public void deleteUserByPrimaryKey(User user){
        String whereCondition = User.COLUMN_NAME_USERNAME + " = '" + user.getUserName() + "'";
        this.getWritableDatabase().delete(User.TABLE_NAME, whereCondition, null);
    }

    public void updateUserByPrimaryKey(User user){

        String sqlUpdate = "UPDATE "+ User.TABLE_NAME + " "
                            + "SET " + User.COLUMN_NAME_PASSWORD + " = '" + user.getPassword() + "', "
                            + User.COLUMN_NAME_NAME + " = '" + user.getName() + "', "
                            + User.COLUMN_NAME_TYPE + " = '" + user.getType() +"' "
                            + "WHERE "+ User.COLUMN_NAME_USERNAME +" = '" + user.getUserName() + "'";
        getWritableDatabase().execSQL(sqlUpdate);
    }

    public ArrayList<User> getUserList(){
       Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + User.TABLE_NAME, null);
       ArrayList<User> users = new ArrayList<>();

        while (cursor.moveToNext()){
            User user = new User();
            user.setUserName(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setType(cursor.getString(3));
            users.add(user);
        }
        return users;
    }

    public User authenticateUser(String username, String password){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + User.TABLE_NAME
                + " WHERE " + User.COLUMN_NAME_USERNAME + "= '"+ username +"'"
                + " AND " + User.COLUMN_NAME_PASSWORD + "= '" + password + "'", null);

        User user = null;

        while (cursor.moveToNext()){
            user = new User();
            user.setUserName(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setType(cursor.getString(3));
            break;
        }
        return user;
    }

    public void insertCar(Car car){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car.COLUMN_NAME_NAME, car.getName());
        contentValues.put(Car.COLUMN_NAME_MODEL, car.getModel());
        contentValues.put(Car.COLUMN_NAME_YEAR, car.getYear());
        contentValues.put(Car.COLUMN_NAME_PRICE, car.getPrice());
        contentValues.put(Car.COLUMN_NAME_COLOR, car.getColor());
        contentValues.put(Car.COLUMN_NAME_VIN, car.getVin());
        contentValues.put(Car.COLUMN_NAME_IMAGE, car.getImage());

        Log.d(TAG, "insertCar: " + car.toString());

        this.getWritableDatabase().insertOrThrow(Car.TABLE_NAME, "", contentValues);
    }

    public void deleteCarByPrimaryKey(Car car){
        String whereCondition = Car.COLUMN_NAME_NAME + " = '" + car.getName() + "'";
        this.getWritableDatabase().delete(Car.TABLE_NAME, whereCondition, null);
    }

    public void updateCarByPrimaryKey(Car car){
        String sqlUpdate = "UPDATE "+ Car.TABLE_NAME + " "
                + "SET " + Car.COLUMN_NAME_MODEL + " = '" + car.getModel() + "', "
                + Car.COLUMN_NAME_YEAR + " = " + car.getYear() + ", "
                + Car.COLUMN_NAME_COLOR + " = '" + car.getColor() + "', "
                + Car.COLUMN_NAME_PRICE + " = '" + car.getPrice() + "', "
                + Car.COLUMN_NAME_VIN + " = '" + car.getVin() + "', "
                + Car.COLUMN_NAME_IMAGE + " = '" + car.getImage() + "' "
                + "WHERE "+ Car.COLUMN_NAME_NAME +" = '" + car.getName() + "'";
        getWritableDatabase().execSQL(sqlUpdate);
    }

    public ArrayList<Car> getCarList(){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + Car.TABLE_NAME, null);
        ArrayList<Car> cars = new ArrayList<>();

        while (cursor.moveToNext()){
            Car car = new Car();

            car.setName(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_NAME)));
            car.setModel(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_MODEL)));
            car.setYear(cursor.getInt(cursor.getColumnIndex(Car.COLUMN_NAME_YEAR)));
            car.setColor(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_COLOR)));
            car.setPrice(cursor.getFloat(cursor.getColumnIndex(Car.COLUMN_NAME_PRICE)));
            car.setVin(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_VIN)));
            car.setImage(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_IMAGE)));

            cars.add(car);
        }
        return cars;
    }

    public Car getCarByPrimaryKey(String carName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + Car.TABLE_NAME
                + " WHERE " + Car.COLUMN_NAME_NAME + " = '" + carName + "'", null);
        Car car = null;

        while (cursor.moveToNext()){
            car = new Car();

            car.setName(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_NAME)));
            car.setModel(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_MODEL)));
            car.setYear(cursor.getInt(cursor.getColumnIndex(Car.COLUMN_NAME_YEAR)));
            car.setColor(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_COLOR)));
            car.setPrice(cursor.getFloat(cursor.getColumnIndex(Car.COLUMN_NAME_PRICE)));
            car.setVin(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_VIN)));
            car.setImage(cursor.getString(cursor.getColumnIndex(Car.COLUMN_NAME_IMAGE)));

        }
        return car;
    }
}

