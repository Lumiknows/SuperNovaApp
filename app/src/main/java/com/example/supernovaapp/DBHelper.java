package com.example.supernovaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "supernovaApp.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT)");

        // Create cart table
        db.execSQL("CREATE TABLE cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "title TEXT, " +
                "studio TEXT, " +
                "price TEXT, " +
                "discount TEXT, " +
                "imageResId INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))");
    }

    // USER
    public boolean checkUserIfExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != 1;
    }

    public int validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }
        else {
            cursor.close();
            return -1;
        }
    }

    // CART
    public boolean insertToCart(int userId, String title, String studio, String price, String discount, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("title", title);
        cv.put("studio", studio);
        cv.put("price", price);
        cv.put("discount", discount);
        cv.put("imageResId", imageResId);
        long result = db.insert("cart", null, cv);
        return result != -1;
    }

    // Modified getCartItemByUserId to handle empty cart gracefully
    public List<CartItem> getCartItemByUserId(int userId) {
        List<CartItem> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE user_id = ?", new String[]{String.valueOf(userId)});

        // Handle empty cart gracefully by checking if the cursor contains data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String studio = cursor.getString(cursor.getColumnIndexOrThrow("studio"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String discount = cursor.getString(cursor.getColumnIndexOrThrow("discount"));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));

                cartList.add(new CartItem(id, title, studio, price, discount, imageResId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cartList;
    }

    public boolean deleteCartItemById(int cartItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("cart", "id = ?", new String[]{String.valueOf(cartItemId)});
        return rowsDeleted > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables and recreate them if version is upgraded
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }
}
