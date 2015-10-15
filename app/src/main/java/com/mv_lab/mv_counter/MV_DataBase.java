package com.mv_lab.mv_counter;

/**
 * Created by admin on 02.01.2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MV_DataBase {
    private static DBHelper dbHelper;
    private static SQLiteDatabase mvDB;

    public static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "MVDB", null, 4);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table indexTable ("
            + "id integer primary key autoincrement,"
            + "name text" + ");");
            Log.d("myLog", "indexTable");

            db.execSQL("create table valueTable ("
                    + "id integer primary key autoincrement,"
                    + "indexId integer,"
                    + "value integer" + ");");
            Log.d("myLog", "valueTable");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(""+oldVersion, "valueTable");
            Log.d(""+newVersion, "valueTable");
            if  (oldVersion == 3 && newVersion == 4) {
                db.execSQL("create table valueTable ("
                        + "id integer primary key autoincrement,"
                        + "indexId integer,"
                        + "value integer" + ");");
                Log.d("myLog", "valueTable");
            }
        }
    }

    public static void initialize(Context context) {
        dbHelper = new DBHelper(context);
        mvDB = dbHelper.getWritableDatabase();
    }

    public static Long WriteIndex(String indexName) {
        ContentValues cv = new ContentValues();
        cv.put("name", indexName);
        long rowID = mvDB.insert("indexTable", null, cv);

        return rowID;
    }

    public static long writeIndexValue(long indexId, int value) {
        ContentValues cv = new ContentValues();
        cv.put("indexId", indexId);
        cv.put("value", value);
        long rowID = mvDB.insert("valueTable", null, cv);

        return rowID;
    }

    public static int readIndexValue(long indexID, boolean indexFound) {
        String [] selectionArgs = new String[1];
        selectionArgs[0] = "" + indexID;

        Cursor cursorAll = mvDB.query("valueTable", null, null, null, null, null, null);
        if (cursorAll.getCount() == 0) {
            indexFound = false;
            return 0;
        }

//        String queryString = "Select ";
        Cursor cursor = mvDB.query("valueTable", null, "indexId = ?", selectionArgs, null, null, null);
        if (cursor == null) {
            indexFound = false;
            return 0;
        }

        if (cursor.getCount() == 0) {
            indexFound = false;
            return 0;
        }

        if (cursor.moveToFirst()) {
            indexFound = true;

            int valueColIndex = cursor.getColumnIndex("value");

            return cursor.getInt(valueColIndex);
        }

        indexFound = false;
        return 0;
    }

    public static HashMap<Long, String> ReadIndex() {
        Cursor cursor = mvDB.query("indexTable", null, null, null, null, null, null);

        HashMap<Long, String> resultMap = new HashMap<Long, String>();
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("name");

            do {
                resultMap.put(cursor.getLong(idColIndex), cursor.getString(nameColIndex));
            }
            while (cursor.moveToNext());
        }
        return resultMap;
    }

    public static void deleteTables(ArrayList<String> tablesList) {
        for (String table : tablesList) {
            int clearCount = mvDB.delete(table, null, null);
        }
        mvDB.close();
    }

    public static void updateIndexValue(long indexId, int value) {
        ContentValues cv = new ContentValues();
        cv.put("indexId", indexId);
        cv.put("value", value);

        int updCount = mvDB.update("valueTable", cv, "indexId = ?", new String[] {"" + indexId});
    }

    public static void deleteIndex(long indexId) {
        mvDB.delete("valueTable", "indexId = ?", new String[] {"" + indexId});
        mvDB.delete("indexTable", "id =  " + indexId, null);
    }
}
