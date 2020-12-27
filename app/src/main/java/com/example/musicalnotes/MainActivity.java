package com.example.musicalnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     private static final String TAG = "Main activity";
     DatabaseHelper mDatabaseHelper;
     private Button btnAdd, btnView;
     private EditText editText;
     private TextView textViewAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAdd = findViewById(R.id.textViewAdd);
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String newEntry = editText.getText().toString();

            if(editText.length() != 0 ) {
                AddData(newEntry);
                editText.setText("");
                int counter = app_preferences.getInt("counter", 0);
                textViewAdd.setText("Data has been added  " + counter + " times.");
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putInt("counter", ++counter);
                editor.apply();
            } else {
                toastMessage("You should write a musical note");
            }
            }

        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

     public void AddData(String newEntry){
         boolean insertData = mDatabaseHelper.addData(newEntry);

         if (insertData) {
             toastMessage("Musical note added successfully, hurray!");
         } else {
             toastMessage("The musical note was not added, it's a pity :(");
         }
     }


    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}