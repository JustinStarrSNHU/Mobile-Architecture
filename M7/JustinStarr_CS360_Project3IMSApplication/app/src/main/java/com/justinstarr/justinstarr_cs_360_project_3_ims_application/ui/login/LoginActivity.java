package com.justinstarr.justinstarr_cs_360_project_3_ims_application.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justinstarr.justinstarr_cs_360_project_3_ims_application.MainActivity;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.R;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.LoginRepository;

// class that handles the login activity
public class LoginActivity extends AppCompatActivity {

    //class attributes

    private Activity activity;
    private Button mLoginButton, mRegisterButton;
    private EditText mEmail, mPassword, mUserNameEditText;
    private String mNameHolder, mPhoneHolder, mEmailHolder, mPasswordHolder;
    private Boolean mEmptyHolder;
    private SQLiteDatabase myDatabase;
    private LoginRepository repository;
    private String mTempPassword = "TEMP_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        mLoginButton = findViewById(R.id.login);
        mRegisterButton = findViewById(R.id.register);
        mEmail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        repository = new LoginRepository(this);
        mUserNameEditText = findViewById(R.id.username);

        // for enabling the login button when text is entered into the User Name EditText
        mUserNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    mLoginButton.setEnabled(false);
                } else {
                    mLoginButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        // when the login button is pressed the login method is called
        mLoginButton.setOnClickListener(view -> {
            login();
        });

        // when the register button is pressed the user is taken to the register screen
        mRegisterButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // logs the user in
    @SuppressLint("Range")
    public void login() {

        // checks to make sure the EditText fields are not empty
        String message = checkEditTextIsNotEmpty();

        // if emptyHolder is true, meaning the EditText fields are not empty, the LoginRepository is queried to search for the user
        if(!mEmptyHolder) {

            myDatabase = repository.getWritableDatabase();

            Cursor cursor = myDatabase.query(LoginRepository.TABLE_NAME, null, " " + LoginRepository.COLUMN_EMAIL + "=?", new String[]{mEmailHolder}, null, null, null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();

                    mTempPassword = cursor.getString(cursor.getColumnIndex(LoginRepository.COLUMN_PASSWORD));
                    mNameHolder = cursor.getString(cursor.getColumnIndex(LoginRepository.COLUMN_NAME));
                    mPhoneHolder = cursor.getString(cursor.getColumnIndex(LoginRepository.COLUMN_PHONE));


                }
            }
            cursor.close();
            repository.close();

            checkLoginResult();
        }
        else {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    // checks to make sure none of the EditText fields are empty
    // if any fields are empty the appropriate message is set
    // to be displayed to the user
    public String checkEditTextIsNotEmpty() {
        String message = "Not Empty";
        mEmailHolder = mEmail.getText().toString().trim();
        mPasswordHolder = mPassword.getText().toString().trim();

        if (mEmailHolder.isEmpty()) {
            mEmail.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your email";
        }
        else if (mPasswordHolder.isEmpty()) {
            mPassword.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your password";

        }
        else {
            mEmptyHolder = false;
        }
        return message;
    }

    // verifies that the password matches the email that is given for the user to log in
    public void checkLoginResult() {
        // if the credentials found matches the credentials entered by the user, it stores the user information and displays a welcome message to the user
        if (mTempPassword.equalsIgnoreCase(mPasswordHolder)) {
            Toast.makeText(LoginActivity.this, "Welcome, " + mEmailHolder.toString().trim() + "!", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("user_name", mNameHolder);
            bundle.putString("user_email", mEmailHolder);
            bundle.putString("user_phone", mPhoneHolder);

            // moves the user to the main screen
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            // clears all the EditText fields
            clearEditText();
        }
        else {
            // Displays a message to the user that the credentials entered were invalid
            Toast.makeText(LoginActivity.this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
        }
        mTempPassword = "USER_NOT_FOUND";
    }

    // clears all the EditText fields
    public void clearEditText() {
        mEmail.getText().clear();
        mPassword.getText().clear();
    }
}