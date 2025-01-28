package com.justinstarr.justinstarr_cs_360_project_3_ims_application.data;

public class User {
    // class attributes
    private int mUserId;
    private String mUserName;
    private String mUserPhone;
    private String mUserEmail;
    private String mUserPassword;

    // constructors
    public User() {
        super();
    }

    public User(int id, String name, String phone, String email, String password) {
        this.mUserId = id;
        this.mUserName = name;
        this.mUserPhone = phone;
        this.mUserEmail = email;
        this.mUserPassword = password;
    }

    public User(String name, String phone, String email, String password) {
        this.mUserName = name;
        this.mUserPhone = phone;
        this.mUserEmail = email;
        this.mUserPassword = password;
    }

    // getters and setters
    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int id) {
        this.mUserId = id;
    }

    public String getUserName() {

        return mUserName;
    }

    public void setUserName(String name) {
        this.mUserName = name;
    }

    public String getUserPhone() {

        return mUserPhone;
    }

    public void setUserPhone(String phone) {

        this.mUserPhone = phone;
    }

    public String getUserEmail() {

        return mUserEmail;
    }

    public void setUserEmail(String email) {

        this.mUserEmail = email;
    }

    public String getUserPassword() {

        return mUserPassword;
    }

    public void setUserPassword(String password) {

        this.mUserPassword = password;
    }
}
