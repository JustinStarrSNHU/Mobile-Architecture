package com.justinstarr.justinstarr_cs_360_project_3_ims_application.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.justinstarr.justinstarr_cs_360_project_3_ims_application.R;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.LoginRepository;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.User;

// class that handles the register activity
public class RegisterActivity extends AppCompatActivity {
    // class attributes
    private EditText mUserNameEditText, mNameHolder, mPhoneHolder, mEmailHolder, mPasswordHolder;
    private Button mCancelButton, mRegisterButton;
    private Boolean mEmptyHolder;
    private SQLiteDatabase database;
    private LoginRepository repository;
    private String mUserFound = "NOT_FOUND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCancelButton = findViewById(R.id.regCancelButton);
        mRegisterButton = findViewById(R.id.regSignupButton);
        mNameHolder = findViewById(R.id.editTextUserName);
        mPhoneHolder = findViewById(R.id.editTextPhoneNumber);
        mEmailHolder = findViewById(R.id.editTextEmailAddress);
        mPasswordHolder = findViewById(R.id.editTextPassword);

        repository = new LoginRepository(this);

        mUserNameEditText = findViewById(R.id.editTextUserName);

        // adds a TextWatcher to the UserName EditText field that enables the register button when the user starts inputing text in the name field
        mUserNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    mRegisterButton.setEnabled(false);
                } else {
                    mRegisterButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        // on click listener for the cancel button, takes the user back to main activity
        mCancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // on click listener for the register button
        mRegisterButton.setOnClickListener(view -> {
            // checks to ensure EditText fields are not empty
            String message = verifyEditTextIsNotEmpty();
            // if the EditText fields are not empty it checks to see if the user email is already in use
            if (!mEmptyHolder) {
                checkIfEmailIsAlreadyInUse(); // registration occurs inside this method if the email is not already in use
                clearEditTextFields(); // clears the EditText fields after it checks if the user exists
            }
            else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show(); // displays the appropriate message if an EditText field is empty
            }
        });
    }

    // checks EditText fields to see if they are empty.
    // assigns the appropriate message to be displayed to the user if any field is empty
    public String verifyEditTextIsNotEmpty() {
        String message = "Not Empty";
        String username = mNameHolder.getText().toString().trim();
        String userPhone = mPhoneHolder.getText().toString().trim();
        String userEmail = mEmailHolder.getText().toString().trim();
        String userPassword = mPasswordHolder.getText().toString().trim();

        if (username.isEmpty()) {
            mNameHolder.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your username";
        }
        else if (userPhone.isEmpty()) {
            mPhoneHolder.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your phone number";
        }
        else if (userEmail.isEmpty()) {
            mEmailHolder.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your email address";
        }
        else if (userPassword.isEmpty()) {
            mPasswordHolder.requestFocus();
            mEmptyHolder = true;
            message = "Please enter your password";
        }
        else {
            mEmptyHolder = false;
        }
        return message;
    }

    // checks if user email is already in use
    public void checkIfEmailIsAlreadyInUse() {
        String email = mEmailHolder.getText().toString().trim();

        database = repository.getWritableDatabase();

        Cursor cursor = database.query(LoginRepository.TABLE_NAME, null, " " + LoginRepository.COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                mUserFound = "USER_FOUND";
            }
        }
        if (mUserFound.equalsIgnoreCase("USER_FOUND")) {
            Toast.makeText(RegisterActivity.this, "This user already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            registerUser();
        }
        cursor.close();
        repository.close();
    }

    // registers the new user if the user does not already exist
    public void registerUser() {
        String username = mNameHolder.getText().toString().trim();
        String userPhone = mPhoneHolder.getText().toString().trim();
        String userEmail = mEmailHolder.getText().toString().trim();
        String userPassword = mPasswordHolder.getText().toString().trim();

        // creates the new user and adds it to the users repository
        User user = new User(username, userPhone, userEmail, userPassword);
        repository.createNewUser(user);
        // Displays a message to the user on the UI that the registration was successful
        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

        // after the user is created, the user is returned to the login screen
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    // clears the EditText fields
    public void clearEditTextFields() {

        // clears all of the EditText fields
        mNameHolder.getText().clear();
        mPhoneHolder.getText().clear();
        mEmailHolder.getText().clear();
        mPasswordHolder.getText().clear();
    }
}
