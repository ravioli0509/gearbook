package com.example.gearbook_nawalage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

public class GearRegistryActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_registry);
        Button confirm = findViewById(R.id.send_button);
        final EditText description = findViewById(R.id.enterDescription);
        final EditText comment = findViewById(R.id.enterComment);
        final EditText price = findViewById(R.id.enterPrice);
        final EditText maker = findViewById(R.id.enterMaker);
        final EditText date = findViewById(R.id.enterDate);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = (ListView) findViewById(R.id.listView);
                // Stringing the text from editText
                String descriptionText = description.getText().toString();
                String commentText = comment.getText().toString();
                String priceText = price.getText().toString();
                String makerText = maker.getText().toString();
                String dateText = date.getText().toString();
                // Validation section, same as GearEditActivity
                if (descriptionText.trim().equals("") || priceText.trim().equals("") || makerText.trim().equals("") || dateText.trim().equals("") ) {
                    Snackbar.make(view, "You have empty sections (exclude comments)", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if (commentText.length() > 20) {
                        Snackbar.make(view, "Comment Too Long!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    } else if (makerText.length() > 20) {
                        Snackbar.make(view, "Maker Too Long!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (descriptionText.length() > 40) {
                        Snackbar.make(view, "Description Too Long!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (!priceText.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                        Snackbar.make(view, "Price value is not correct", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (!dateText.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
                        Snackbar.make(view, "Date is not correct (YYYY-MM-DD)", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result_description", descriptionText);
                        resultIntent.putExtra("result_maker", makerText);
                        resultIntent.putExtra("result_price", Float.parseFloat(priceText));
                        resultIntent.putExtra("result_comment", commentText);
                        resultIntent.putExtra("result_date", dateText);
                        // To Fire method in MainActivity.OnActivityResult
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }
            }
        });

    }
}