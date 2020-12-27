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

public class EditDataActivity extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";

    private Button btnSave, btnDelete;
    private EditText editText;
    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedId;
    private TextView textViewEdit, textViewDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data2);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        editText = findViewById(R.id.editText4);
        textViewEdit = findViewById(R.id.textViewEdit);
        textViewDelete = findViewById(R.id.textViewDeleteSense);
        mDatabaseHelper = new DatabaseHelper(this);
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("_id",-1);
        selectedName = receivedIntent.getStringExtra("name");
        editText.setText(selectedName);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editText.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item, selectedId, selectedName);
                    int counter = app_preferences.getInt("counterEdit", 0);
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putInt("counterEdit", ++counter);
                    editor.apply();
                    textViewEdit.setText("Data has been edited " + counter + " times.");
                    toastMessage("Musical note has been edited");
                }else{
                    toastMessage("You must enter a note");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedId,selectedName);
                editText.setText("");
                toastMessage("Delete from Database");
                int counter = app_preferences.getInt("counterDelete", 0);
                textViewDelete.setText("Data has been deleted " + counter + " times.");
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putInt("counterDelete", ++counter);
                editor.apply();
            }
        });

    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void goToViewData(View view) {
        Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
        startActivity(intent);
    }
}