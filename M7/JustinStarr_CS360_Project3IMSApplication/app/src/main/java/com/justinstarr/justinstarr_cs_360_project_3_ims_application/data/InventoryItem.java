package com.justinstarr.justinstarr_cs_360_project_3_ims_application.data;

public class InventoryItem {

    // class attributes
    private int mItemId;

    private String mUserEmail, mItemDescription, mItemQuantity;

    // constructors
    public InventoryItem() { super(); }

    public InventoryItem(int itemId, String userEmail, String itemDescription, String itemQuantity) {
        super();
        this.mItemId = itemId;
        this.mUserEmail = userEmail;
        this.mItemDescription = itemDescription;
        this.mItemQuantity = itemQuantity;
    }

    public InventoryItem(String userEmail, String itemDescription, String itemQuantity) {
        this.mUserEmail = userEmail;
        this.mItemDescription = itemDescription;
        this.mItemQuantity = itemQuantity;
    }

    // getters and setters

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int itemId) {
        this.mItemId = itemId;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.mUserEmail = userEmail;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.mItemDescription = itemDescription;
    }

    public String getItemQuantity() {
        return mItemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.mItemQuantity = itemQuantity;
    }
}
