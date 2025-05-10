package com.nasaro.waylantaway.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nasaro.waylantaway.Models.User;


public class SqlHelper extends SQLiteOpenHelper {
    public SqlHelper(Context context) {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.onCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(User.onDrop);
    }

    public long insert(String table, ContentValues contentValues)
    {
        return getWritableDatabase().insert(table, null, contentValues);
    }

    public int update(String table, ContentValues contentValues, String whereClause, String[] whereArgs)
    {
        return getWritableDatabase().update(table, contentValues, whereClause, whereArgs);
    }
    public Cursor query(String table, String whereClause, String[] whereArgs)
    {
        return getReadableDatabase().query(table, null, whereClause, whereArgs, null, null, null);
    }

    public Cursor getAll(String table)
    {
        return query(table, null, null);

    }
    public int delete(String table, String whereClause, String[] whereArgs)
    {
        return getWritableDatabase().delete(table, whereClause, whereArgs);
    }
}
