package com.justinstarr.justinstarr_cs_360_project_3_ims_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItemsRepository;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItem;

public class AddItemActivity extends AppCompatActivity {
    // class attributes
    private TextView mUserEmail;
    private String mEmailHolder, mDescriptionHolder, mQuantityHolder;
    private ImageButton mIncreaseItemQuantity, mDecreaseItemQuantity;
    private EditText mInventoryItemDescription, mInventoryItemQuantity;
    private Button mCancelButton, mAddInventoryItemButton;
    private Boolean mEmptyHolder;
    private InventoryItemsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // initialize Button, TextView, and EditText variables
        mUserEmail = findViewById(R.id.textViewLoggedUser);
        mInventoryItemDescription = findViewById(R.id.editTextItemDescription);
        mIncreaseItemQuantity = findViewById(R.id.itemQtyIncrease);
        mDecreaseItemQuantity = findViewById(R.id.itemQtyDecrease);
        mInventoryItemQuantity = findViewById(R.id.editTextItemQuantity);
        mCancelButton = findViewById(R.id.addCancelButton);
        mAddInventoryItemButton = findViewById(R.id.addItemButton);
        repository = new InventoryItemsRepository(this);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());

        // Receiving user email send by MainActivity
        mEmailHolder = intent.get().getStringExtra(MainActivity.mUserEmail);

        // Setting user email on textViewLoggedUser
        mUserEmail.setText(getString(R.string.logged_user, mEmailHolder));

        // onClickListener for item quantity increase button
        mIncreaseItemQuantity.setOnClickListener(view -> {
            int mNumber = 0, mTotal;

            String mItemQuantityValue = mInventoryItemQuantity.getText().toString().trim();

            if (!mItemQuantityValue.isEmpty()) {
                mNumber = Integer.parseInt(mItemQuantityValue);
            }

            mTotal = mNumber + 1;
            mInventoryItemQuantity.setText(String.valueOf(mTotal));
        });

        // onClickListener for item quantity decrease button
        mDecreaseItemQuantity.setOnClickListener(view -> {
            int mNumber = 0, mTotal;

            String mItemQuantityValue = mInventoryItemQuantity.getText().toString().trim();

            if (mItemQuantityValue.equals("0")) {
                Toast.makeText(this, "Item Quantity Cannot Be Less Than Zero", Toast.LENGTH_SHORT).show();
            }
            else {
                mNumber = Integer.parseInt(mItemQuantityValue);
                mTotal = mNumber - 1;
                mInventoryItemQuantity.setText(String.valueOf(mTotal));
            }
        });

        // onClickListener for the cancel button
        mCancelButton.setOnClickListener(view -> {
            Intent newIntent = new Intent();
            setResult(0, newIntent);
            this.finish();
        });

        // onClick Listener for add item button
        mAddInventoryItemButton.setOnClickListener(view -> {
            String mMessage = checkEditTextFieldsNotEmpty();

            if (!mEmptyHolder) {
                String mEmail = mEmailHolder;
                String mDescription = mDescriptionHolder;
                String mQuantity = mQuantityHolder;

                if(mQuantityHolder.equals("0")) {
                    Toast.makeText(this, mMessage, Toast.LENGTH_SHORT).show();
                }
                // creates the item if the quantity is greater than zero
                else {
                    // creates the new inventory item
                    InventoryItem inventoryItem = new InventoryItem(mEmail, mDescription, mQuantity);
                    // adds the new inventory item to the inventory items repository
                    repository.createNewInventoryItem(inventoryItem);

                    // displays a success message to the user
                    Toast.makeText(this, "Item Successfully Added To Inventory", Toast.LENGTH_SHORT);

                    // return to MainActivity
                    Intent newIntent = new Intent();
                    setResult(RESULT_OK, newIntent);
                    this.finish();
                }
            }
        });
    }
    public String checkEditTextFieldsNotEmpty() {
        // Getting value from fields and storing into string variable
        String mMessage = "";
        mDescriptionHolder = mInventoryItemDescription.getText().toString().trim();
        mQuantityHolder = mInventoryItemQuantity.getText().toString().trim();

        // sets the appropriate message
        // if quantity is zero
        if (mQuantityHolder.equals("0")) {
            mInventoryItemQuantity.requestFocus();
            mEmptyHolder = true;
            mMessage = "Item Quantity Must Be Greater Than Zero";
        }
        // if the description field is empty
        if (mDescriptionHolder.isEmpty()) {
            mInventoryItemDescription.requestFocus();
            mEmptyHolder = true;
            mMessage = "Item Description is Empty";
        }
        // if all conditions are satisfied the empty holder is set to false
        else {
            mEmptyHolder = false;
        }
        return mMessage;
    }
}