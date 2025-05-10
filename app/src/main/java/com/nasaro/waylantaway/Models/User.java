package com.nasaro.waylantaway.Models;

import android.content.ContentValues;
import android.database.Cursor;


public class User
{
    public String email;
    public String name;
    public  int id;
    public static String table_name = "users";
    public static String onDrop = "DROP TABLE IF EXISTS users";
    public static String onCreate = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)";

    public User(){}
    public User(String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public ContentValues toValues()
    {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        return values;
    }
    public static User fromCursor(Cursor cursor)
    {
        User user = new User();
        user.id = cursor.getInt(0);
        user.name = cursor.getString(1);
        user.email = cursor.getString(2);
        return user;
    }
}
