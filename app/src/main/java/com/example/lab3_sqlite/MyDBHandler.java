package com.example.lab3_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String Table_Name = "products";
    private static final String Column_ID = "ID";
    private static final String Column_Product_Name = "name";
    private static final String Column_Product_Price = "price";
    private static final String Database_Name = "product.sqLiteDatabase";
    private static final int Database_Version = 1;
    private static SQLiteDatabase sqLiteDatabase;

    public MyDBHandler(Context context){
        super(context, Database_Name, null, Database_Version);
        sqLiteDatabase = this.getReadableDatabase();
        onUpgrade(sqLiteDatabase,0,0);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + Table_Name +
                 "(" + Column_ID + "INTEGER PRIMARY KEY, " +
                 Column_Product_Name + " TEXT, " +
                 Column_Product_Price + " DOUBLE " + ")";
        sqLiteDatabase.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData() {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_Name;
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getDataByName(String name){
        String query = "SELECT * FROM " + Table_Name + " WHERE " + Column_Product_Name + " LIKE '" + name + "%'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getDataByPrice(String price){
        String query = "SELECT * FROM " + Table_Name + " WHERE " + Column_Product_Price + " = '" + price + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getDataByNameAndPrice(String name, String price){
        String query = "SELECT * FROM " + Table_Name + " WHERE " + Column_Product_Name + " LIKE '" + name + "%' AND " + Column_Product_Price + " = '" + price + "'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public void addProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column_Product_Name, product.getProductName());
        values.put(Column_Product_Price, product.getProductPrice());

        sqLiteDatabase.insert(Table_Name, null, values);
        sqLiteDatabase.close();
    }

    public void deleteProduct(String name){
        String cmd = "DELETE FROM " + Table_Name + " WHERE " + Column_Product_Name + " = '" + name + "'";
        sqLiteDatabase.execSQL(cmd);
    }
}
