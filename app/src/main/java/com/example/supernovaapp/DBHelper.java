package com.example.supernovaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        // Create users table with new bio and profile_pic_uri columns
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "bio TEXT, " +                      // NEW column for bio
                "profile_pic_uri TEXT)");          // NEW column for profile picture URI

        // Create cart table
        db.execSQL("CREATE TABLE cart (" +
                "cartId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "title TEXT, " +
                "studio TEXT, " +
                "price TEXT, " +
                "discount TEXT, " +
                "imageResId INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))");

        // Create games table
        db.execSQL("CREATE TABLE games (" +
                "game_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "studio TEXT, " +
                "price TEXT, " +
                "discount TEXT, " +
                "imageResId INTEGER)");

        // Create library table
        db.execSQL("CREATE TABLE library(" +
                "library_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "lib_title TEXT," +
                "lib_studio TEXT, " +
                "hrs TEXT, " +
                "lib_imageResId INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES users(id))");
    }

    // USER methods
    public boolean checkUserIfExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateUsername(int userId, String newUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", newUsername);
        int rowsAffected = db.update("users", cv, "id = ?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0;
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public int validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        } else {
            cursor.close();
            return -1;
        }
    }

    public String getUsernameById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        String username = null;
        if (cursor.moveToFirst()) {
            username = cursor.getString(0);
        }
        cursor.close();
        return username;
    }

    // NEW: Get bio by userId
    public String getBioById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT bio FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        String bio = null;
        if (cursor.moveToFirst()) {
            bio = cursor.getString(0);
        }
        cursor.close();
        return bio;
    }

    // NEW: Update bio
    public boolean updateBio(int userId, String bio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bio", bio);
        int rows = db.update("users", cv, "id = ?", new String[]{String.valueOf(userId)});
        return rows > 0;
    }

    // NEW: Get profile picture URI by userId
    public String getProfilePicUriById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT profile_pic_uri FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        String uri = null;
        if (cursor.moveToFirst()) {
            uri = cursor.getString(0);
        }
        cursor.close();
        return uri;
    }

    // NEW: Update profile picture URI
    public boolean updateProfileImageUri(int userId, String uri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("profile_pic_uri", uri);
        int rows = db.update("users", cv, "id = ?", new String[]{String.valueOf(userId)});
        return rows > 0;
    }

    // CART methods
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

    public List<CartItem> getCartItemByUserId(int userId) {
        List<CartItem> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE user_id = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("cartId"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String studio = cursor.getString(cursor.getColumnIndexOrThrow("studio"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String discount = cursor.getString(cursor.getColumnIndexOrThrow("discount"));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));

                cartList.add(new CartItem(id, title, studio, price, discount, imageResId));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();

        return cartList;
    }

    public boolean deleteCartItemById(int cartItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("cart", "cartId = ?", new String[]{String.valueOf(cartItemId)});
        return rowsDeleted > 0;
    }

    // Library methods
    public boolean insertToLibrary(int userId, String title, String studio, String hrs, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", userId);
        cv.put("lib_title", title);
        cv.put("lib_studio", studio);
        cv.put("hrs", hrs);
        cv.put("lib_imageResId", imageResId);
        long result = db.insert("library", null, cv);
        return result != -1;
    }

    public List<LibraryItem> getLibraryByUserId(int userId) {
        List<LibraryItem> libraryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM library WHERE userId = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("library_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("lib_title"));
                String studio = cursor.getString(cursor.getColumnIndexOrThrow("lib_studio"));
                String hrs = cursor.getString(cursor.getColumnIndexOrThrow("hrs"));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("lib_imageResId"));

                libraryList.add(new LibraryItem(id, title, studio, hrs, imageResId));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();

        return libraryList;
    }

    // Check if item is already in cart
    public boolean isInCart(int userId, String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE user_id = ? AND title = ?", new String[]{String.valueOf(userId), title});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check if item is already in library
    public boolean isInLibrary(int userId, String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM library WHERE userId = ? AND lib_title = ?", new String[]{String.valueOf(userId), title});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS library");
        db.execSQL("DROP TABLE IF EXISTS games");
        onCreate(db);
    }
}
