package com.justinstarr.justinstarr_cs_360_project_3_ims_application;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItemsRepository;
import com.justinstarr.justinstarr_cs_360_project_3_ims_application.data.InventoryItem;

// responsible for populating the list of inventory items
public class InventoryItemsList extends BaseAdapter {
    // class attributes
    private final Activity context;
    private static PopupWindow popupWindow;
     ArrayList<InventoryItem> inventoryItems;
    InventoryItemsRepository repository;

    public InventoryItemsList(Activity context, ArrayList<InventoryItem> inventoryItems, InventoryItemsRepository repository) {
        this.context = context;
        this.inventoryItems = inventoryItems;
        this.repository = repository;
    }

    public static class ViewHolder {
        TextView mTextViewItemId, mTextViewUserEmail, mTextViewItemDescription, mTextViewItemQuantity;
        ImageButton mEditButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            row = inflater.inflate(R.layout.item_row_layout, null, true);

            viewHolder.mEditButton = row.findViewById(R.id.editButton);
            viewHolder.mTextViewItemId = row.findViewById(R.id.textViewItemId);
            viewHolder.mTextViewUserEmail = row.findViewById(R.id.textViewUserEmail);
            viewHolder.mTextViewItemDescription = row.findViewById(R.id.textViewItemDescription);
            viewHolder.mTextViewItemQuantity = row.findViewById(R.id.textViewItemQuantity);

            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextViewItemId.setText("" + inventoryItems.get(position).getItemId());
        viewHolder.mTextViewUserEmail.setText(inventoryItems.get(position).getUserEmail());
        viewHolder.mTextViewItemDescription.setText(inventoryItems.get(position).getItemDescription());
        viewHolder.mTextViewItemQuantity.setText(inventoryItems.get(position).getItemQuantity());

        final int positionPopup = position;

        viewHolder.mEditButton.setOnClickListener(view -> editPopup(positionPopup));

        return row;
    }

    public Object getItem(int position) {
        return position;
    }

    // returns the items position in the inventory list
    public long getItemId(int position) {
        return position;
    }

    // returns the number of items in inventory
    public int getCount() {
        return inventoryItems.size();
    }

    // when an items edit button is selected a popup window is created and displays the items
    // information and the user can edit the items details or choose to delete
    // the item from inventory
    public void editPopup(final int positionPopup) {
        LayoutInflater inflater = context.getLayoutInflater();

        View layout = inflater.inflate(R.layout.activity_edit_item_popup, context.findViewById(R.id.popup_element));

        popupWindow = new PopupWindow(layout, 800, 1000, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        final EditText editItemDescription = layout.findViewById(R.id.editTextItemDescriptionPopup);
        final EditText editItemQuantity = layout.findViewById(R.id.editTextItemQtyPopup);

        editItemDescription.setText(inventoryItems.get(positionPopup).getItemDescription());
        editItemQuantity.setText(inventoryItems.get(positionPopup).getItemQuantity());

        Button mSave = layout.findViewById(R.id.editSaveButton);
        Button mCancel = layout.findViewById(R.id.editCancelButton);
        Button mDeleteItem = layout.findViewById(R.id.delete_item_from_inventory);

        // on click listener for the save button
        mSave.setOnClickListener(view -> {
            String itemDescription = editItemDescription.getText().toString();
            String itemQuantity = editItemQuantity.getText().toString();

            InventoryItem inventoryItem = inventoryItems.get(positionPopup);
            inventoryItem.setItemDescription(itemDescription);
            inventoryItem.setItemQuantity(itemQuantity);

            repository.updateInventoryItem(inventoryItem);
            inventoryItems = (ArrayList<InventoryItem>) repository.getItemsInInventory();

            // if the items quantity was changed to zero and SMS mesasge is sent to the device if permission has been allowed
            Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
            if (itemQuantity.equals("0")) {
                MainActivity.SendSMSMessage(context.getApplicationContext());
            }
            popupWindow.dismiss();
        });

        // on click listener takes the user back to the main screen when the cancel button is hit
        mCancel.setOnClickListener(view -> {
            Toast.makeText(context, "Action Canceled", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        // on click listener for the delete item from inventory button
        mDeleteItem.setOnClickListener(view -> {
            InventoryItem inventoryItem = inventoryItems.get(positionPopup);
            repository.deleteInventoryItem(inventoryItem);

            inventoryItems = (ArrayList<InventoryItem>) repository.getItemsInInventory();

            int mNumberOfItemsInInventory = repository.getTotalNumberOfItemsInInventory();
            TextView TotalItems = context.findViewById(R.id.textViewTotalItemsCount);
            TotalItems.setText(String.valueOf(mNumberOfItemsInInventory));
            notifyDataSetChanged();

            Toast.makeText(context, "Item Deleted From Inventory", Toast.LENGTH_SHORT).show();

            popupWindow.dismiss();
        });
    }
}