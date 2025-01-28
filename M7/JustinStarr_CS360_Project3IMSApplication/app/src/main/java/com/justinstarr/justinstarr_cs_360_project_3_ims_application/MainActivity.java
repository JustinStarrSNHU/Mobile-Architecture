package com.justinstarr.justinstarr_cs_360_project_3_ims_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItemsRepository;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItem;

public class MainActivity extends AppCompatActivity {
    // class attributes
    private TextView mUserName, mTotalNumberOfItems;
    private ImageButton mAddNewItemButton, mSmsButton, mDeleteInventoryButton;
    private ListView mInventoryItemsListView;
    private InventoryItemsRepository repository;
    private static String mNameHolder, mEmailHolder, mPhoneHolder;
    private AlertDialog mAlertDialog = null;
    private ArrayList<InventoryItem> inventoryItems;
    private InventoryItemsList inventoryItemsList;
    int mNumberOfItemsInInventory;
    static final String mUserEmail = "";
    private static int USER_PERMISSION = 0;
    private static boolean allowSms = false;
    private static boolean mDeleteAllInventoryItems = false;

// when main activity starts, it displays all of the items that are in the inventory repository if there are any at all.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.textViewUserNameLabel);
        mTotalNumberOfItems = findViewById(R.id.textViewTotalItemsCount);
        mAddNewItemButton = findViewById(R.id.addNewItemButton);
        mSmsButton = findViewById(R.id.smsNotificationButton);
        mDeleteInventoryButton = findViewById(R.id.deleteInventoryButton);
        mInventoryItemsListView = findViewById(R.id.bodyListView);

        repository = new InventoryItemsRepository(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mNameHolder = bundle.getString("user_name");
            mEmailHolder = bundle.getString("user_email");
            mPhoneHolder = bundle.getString("user_phone");

            // displays the users name and email sent from LoginActivity
            mUserName.setText("   Welcome,  " + mNameHolder + "!");
        }

        inventoryItems = (ArrayList<InventoryItem>) repository.getItemsInInventory();

        mNumberOfItemsInInventory = repository.getTotalNumberOfItemsInInventory();

        if (mNumberOfItemsInInventory > 0) {
            inventoryItemsList = new InventoryItemsList(this, inventoryItems, repository);
            mInventoryItemsListView.setAdapter(inventoryItemsList);
        }
        else {
            Toast.makeText(this, "There are no items in inventory", Toast.LENGTH_SHORT).show();
        }

        mTotalNumberOfItems.setText(String.valueOf(mNumberOfItemsInInventory));

        // onClickListener for the add item button
        mAddNewItemButton.setOnClickListener(view -> {

            Intent add = new Intent(this, AddItemActivity.class);
            add.putExtra(mUserEmail, mEmailHolder);
            startActivityForResult(add, 1);
        });

        // onClickListener for SMS button
        mSmsButton.setOnClickListener(view -> {

            // Request sms permission for the device
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // if the device permission has not been enabled
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this,"Device SMS Permission is Needed", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.SEND_SMS},
                            USER_PERMISSION);
                }
            }
            else {
                // displays a message that device sms permission is allowed
                Toast.makeText(this,"Device SMS Permission is Allowed", Toast.LENGTH_SHORT).show();
            }
            // Open SMS Alert Dialog
            mAlertDialog = SMSNotificationAlertDialog.doubleButton(this);
            mAlertDialog.show();
        });

        // on click listener for the delete inventory button
        mDeleteInventoryButton.setOnClickListener(view -> {
            mNumberOfItemsInInventory = repository.getTotalNumberOfItemsInInventory();
            // only deletes a repository if the repository is not empty
            if (mNumberOfItemsInInventory > 0) {
                // Open Delete Alert Dialog
                mAlertDialog = DeleteItemsAlertDialog.doubleButton(this);
                mAlertDialog.show();

                mAlertDialog.setCancelable(true);
                mAlertDialog.setOnCancelListener(dialog -> deleteAllItemsInInventory());
            } else {
                // displays a message to the user that the repository is empty
                Toast.makeText(this, "Database is Empty", Toast.LENGTH_SHORT).show();
            }
        });

    }
    // called from the delete items alert dialog if the user chooses TO delete all the items in inventory
    public static void YesDeleteItems() {
        mDeleteAllInventoryItems = true;
    }

    // called from the delete items alert dialog if the user chooses NOT to delete all the items in inventory
    public static void NoDeleteItems() {
        mDeleteAllInventoryItems = false;
    }

    // method to delete al the items from the inventory's repository
    private void deleteAllItemsInInventory() {
        if (mDeleteAllInventoryItems) {
            repository.deleteAllInventoryItems();
            Toast.makeText(this, "All Items were Deleted", Toast.LENGTH_SHORT).show();
            mTotalNumberOfItems.setText("0");

            if (inventoryItemsList == null) {
                inventoryItemsList = new InventoryItemsList(this, inventoryItems, repository);
                mInventoryItemsListView.setAdapter(inventoryItemsList);
            }

            inventoryItemsList.inventoryItems = (ArrayList<InventoryItem>) repository.getItemsInInventory();
            ((BaseAdapter) mInventoryItemsListView.getAdapter()).notifyDataSetChanged();
        }
    }

    // when an activity finishes, this method is called to update the UI
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mNumberOfItemsInInventory = repository.getTotalNumberOfItemsInInventory();
                mTotalNumberOfItems.setText(String.valueOf(mNumberOfItemsInInventory));

                if(inventoryItemsList == null)	{
                    inventoryItemsList = new InventoryItemsList(this, inventoryItems, repository);
                    mInventoryItemsListView.setAdapter(inventoryItemsList);
                }

                inventoryItemsList.inventoryItems = (ArrayList<InventoryItem>) repository.getItemsInInventory();
                ((BaseAdapter)mInventoryItemsListView.getAdapter()).notifyDataSetChanged();

            }
            else {
                Toast.makeText(this, "Action Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // this method is called from SMSNotificationsAlertDialog when the user chooses TO allow the application to send sms messasges
    public static void AllowSendSMS() {
        allowSms = true;
    }

    // this method is called from SMSNotificationsAlertDialog when the user chooses NOT TO allow the application to send sms messasges
    public static void DenySendSMS() {
        allowSms = false;
    }

    // method sends a message to the device when an items quantity has been reduced to zero
    public static void SendSMSMessage(Context context) {

        String mSmsMessage = "You have items in inventory that have been reduced to zero and require your attention.";
        String mPhoneNumber = mPhoneHolder;

        if (allowSms) {
            try {
                // if Sms permission has been granted, send sms message to the users phone number
                SmsManager mSmsManager = SmsManager.getDefault();
                mSmsManager.sendTextMessage(mPhoneNumber, null, mSmsMessage, null, null);
                Toast.makeText(context, "SMS Message Sent", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                // an exception is thrown when device sms permissions have been denied. No SMS message is sent
                Toast.makeText(context, "Device SMS Permissions were denied", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            // Displays a message to the user that the application's alerts are disabled
            Toast.makeText(context, "IMS Application Alerts Are Diabled", Toast.LENGTH_SHORT).show();
        }
    }

}