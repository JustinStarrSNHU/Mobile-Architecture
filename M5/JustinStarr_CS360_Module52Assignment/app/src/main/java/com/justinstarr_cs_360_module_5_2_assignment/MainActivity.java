package com.justinstarr_cs_360_module_5_2_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mNameTextEditText;
    private TextView mTextGreetingTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextEditText = findViewById(R.id.nameText);
        mTextGreetingTextView = findViewById(R.id.textGreeting);

        Button button = findViewById(R.id.buttonSayHello);
        button.setEnabled(false);

        mNameTextEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void SayHello(View view) {

        // This is the only way I could get it to check if nameText was null
        if(mNameTextEditText.getText().toString().trim().length()==0) {
            mTextGreetingTextView.setText("You must enter a name");
        }
        else {
            String text = mNameTextEditText.getText().toString();
            mTextGreetingTextView.setText("Hello, " + text + "!");
        }

    }
}