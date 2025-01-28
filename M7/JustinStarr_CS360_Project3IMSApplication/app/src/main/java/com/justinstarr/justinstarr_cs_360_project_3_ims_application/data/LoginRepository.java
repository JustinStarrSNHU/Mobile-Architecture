package com.justinstarr.justinstarr_cs_360_project_3_ims_application.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginRepository extends SQLiteOpenHelper {
    // class attributes
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IMSUsersRepo.DB"; // Inventory Management System Users Repo
    public static final String TABLE_NAME = "IMS_TABLE_OF_USERS";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_OF_USERS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_NAME + " VARCHAR, " +
            COLUMN_PHONE + " VARCHAR, " +
            COLUMN_EMAIL + " VARCHAR, " +
            COLUMN_PASSWORD + " VARCHAR" + ");";

    public LoginRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_OF_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    // when called, creates a new user
    public void createNewUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getUserName());
        contentValues.put(COLUMN_PHONE, user.getUserPhone());
        contentValues.put(COLUMN_EMAIL, user.getUserEmail());
        contentValues.put(COLUMN_PASSWORD, user.getUserPassword());

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    // looks for a user based on the passed in user id
    public User readUser(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_PASSWORD }, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        cursor.close();

        return user;
    }

    // updates a user
    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getUserName());
        contentValues.put(COLUMN_PHONE, user.getUserPhone());
        contentValues.put(COLUMN_EMAIL, user.getUserEmail());
        contentValues.put(COLUMN_PASSWORD, user.getUserPassword());

        return database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] {String.valueOf(user.getUserId()) });
    }

    public void deleteUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(user.getUserId()) });
        database.close();
    }

    // returns a list of users in the repository
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setUserPhone(cursor.getString(2));
                user.setUserEmail(cursor.getString(3));
                user.setUserPassword(cursor.getString(4));

                usersList.add(user);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return usersList;
    }

    // deletes all the users in the database
    public void deleteAllUsers() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
        database.close();
    }

    // returns the total number of users in the repository
    public int getNumUsers() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int totalNumberOfUsers = cursor.getCount();
        cursor.close();

        return totalNumberOfUsers;
    }
}