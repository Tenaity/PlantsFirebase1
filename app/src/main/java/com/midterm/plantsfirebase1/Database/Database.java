package com.midterm.plantsfirebase1.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.midterm.plantsfirebase1.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "PlantOrderDB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productId","productName","price","quantity"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("productId")),
                        c.getString(c.getColumnIndex("productName")),
                        c.getString(c.getColumnIndex("price")),
                        c.getString(c.getColumnIndex("quantity"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(productId,productName,price,quantity) VALUES('%s', '%s','%s','%s')",
                order.getProductId(),order.getProductName(),order.getPrice(),order.getQuantity());
        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void addToFavorites (String productid){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(productid) VALUES('%s');",productid);
        db.execSQL(query);
    }

    public void delToFavorites (String productid){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE productid='%s';",productid);
        db.execSQL(query);
    }

    public Boolean isToFavorites (String productid){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE productid='%s';",productid);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() <=0 ){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
