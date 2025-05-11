package com.nasaro.waylantaway.Models;
import android.content.ContentValues;
import android.database.Cursor;

public class Car {
    public int id;
    public String make;
    public String model;
    public int year;
    public long user_id;
    public static String tableName = "cars";
    public static String create = "CREATE TABLE " + tableName + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "make TEXT," +
            "model TEXT," +
            "year INTEGER," +
            "user_id INTEGER, " +
            "FOREIGN KEY (user_id) REFERENCES " + User.table_name + "(id))";
    public static String drop = "DROP TABLE IF EXISTS " + tableName;

    public Car(){}
    public Car(String make, String model, int year, long user_id) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.user_id = user_id;

    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("make", make);
        values.put("model", model);
        values.put("year", year);
        values.put("user_id", user_id);

        return values;
    }

    public static Car fromCursor(Cursor cursor) {
        Car car = new Car();
        car.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        car.make = cursor.getString(cursor.getColumnIndexOrThrow("make"));
        car.model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
        car.year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));

        return car;
    }

}

