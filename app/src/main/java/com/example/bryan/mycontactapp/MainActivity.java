package com.example.bryan.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.bryan.mycontactapp.R;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editPhone, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editEmail = findViewById(R.id.editText_email);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString());
        if (isInserted) {
            Toast.makeText(this, "Success - Contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failed - Contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor");

        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer sb = new StringBuffer();
        while(res.moveToNext()) {
            // append res column 0,1,2,3 to buffer - see StringBuffer and Cursor API
            // delimit each of the "appends" with line feed "\n"
            for (int i = 0; i < 4; i++) {
                sb.append(res.getColumnName(i) + ": " + res.getString(i) + "\n");
            }
            sb.append("\n");
        }

        showMessage("Data", sb.toString());
    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialogue");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
