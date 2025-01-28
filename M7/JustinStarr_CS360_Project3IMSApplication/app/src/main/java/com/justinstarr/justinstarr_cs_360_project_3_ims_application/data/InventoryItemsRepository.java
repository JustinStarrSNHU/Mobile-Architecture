package com.justinstarr.justinstarr_cs_360_project_3_ims_application.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryItemsRepository extends SQLiteOpenHelper {
    // class attributes
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "IMSItemsRepo.DB";
    private static final String TABLE_NAME = "IMS_TABLE_OF_ITEMS";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";

    private static final String CREATE_TABLE_OF_ITEMS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_EMAIL + " VARCHAR, " +
            COLUMN_ITEM_DESCRIPTION + " VARCHAR, " +
            COLUMN_ITEM_QUANTITY + " VARCHAR" + ");";

    public InventoryItemsRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_OF_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    // add an item to the items repository
    public void createNewInventoryItem(InventoryItem inventoryItem) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, inventoryItem.getUserEmail());
        contentValues.put(COLUMN_ITEM_DESCRIPTION, inventoryItem.getItemDescription());
        contentValues.put(COLUMN_ITEM_QUANTITY, inventoryItem.getItemQuantity());

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    //Read an item from the repository
    public InventoryItem readInventoryItem(int itemId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                new String[] { COLUMN_ID, COLUMN_EMAIL, COLUMN_ITEM_DESCRIPTION, COLUMN_ITEM_QUANTITY }, COLUMN_ID + " = ?",
                new String[] { String.valueOf(itemId) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        InventoryItem inventoryItem = new InventoryItem(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();

        return inventoryItem;
    }

    // update an item in the repository
    public int updateInventoryItem(InventoryItem inventoryItem) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_EMAIL, inventoryItem.getUserEmail());
        contentValues.put(COLUMN_ITEM_DESCRIPTION, inventoryItem.getItemDescription());
        contentValues.put(COLUMN_ITEM_QUANTITY, inventoryItem.getItemQuantity());

        return database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] { String.valueOf(inventoryItem.getItemId()) });

    }

    // delete an item from the repository

    public void deleteInventoryItem(InventoryItem inventoryItem) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(inventoryItem.getItemId()) });
        database.close();

    }

    // Get all items in the repository
    public List<InventoryItem> getItemsInInventory() {

        List<InventoryItem> inventoryItemsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // iterates over the database and adds each item to the list of inventory items
        if (cursor.moveToFirst()) {
            do {
                InventoryItem inventoryItem = new InventoryItem();
                inventoryItem.setItemId(Integer.parseInt(cursor.getString(0)));
                inventoryItem.setUserEmail(cursor.getString(1));
                inventoryItem.setItemDescription(cursor.getString(2));
                inventoryItem.setItemQuantity(cursor.getString(3));

                inventoryItemsList.add(inventoryItem);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return inventoryItemsList;
    }

    // deletes all items in the inventory repository
    public void deleteAllInventoryItems() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
        database.close();
    }

    // returns the total number of items in the inventory repository
    public int getTotalNumberOfItemsInInventory() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int totalNumberOfItems = cursor.getCount();
        cursor.close();
        return totalNumberOfItems;
    }
}
