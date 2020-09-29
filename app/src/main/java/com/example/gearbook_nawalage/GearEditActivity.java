package com.example.gearbook_nawalage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;


public class GearEditActivity extends AppCompatActivity {

    public static final String METHOD = "com.example.gearbook_nawalage.example.METHOD";
    public static final String POS = "com.example.gearbook_nawalage.example.POS";
    public static final String EDITDESC = "com.example.gearbook_nawalage.example.EDITDESC";
    public static final String EDITMAKER = "com.example.gearbook_nawalage.example.EDITMAKER";
    public static final String EDITPRICE = "com.example.gearbook_nawalage.example.EDITPRICE";
    public static final String EDITDATE = "com.example.gearbook_nawalage.example.EDITDATE";
    public static final String EDITCOMMENT = "com.example.gearbook_nawalage.example.EDITCOMMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_edit);
        Button editButton = findViewById(R.id.edit_button);
        Button deleteButton = findViewById(R.id.delete_button);
        final EditText editDescription = findViewById(R.id.editDescription);
        final EditText editComment = findViewById(R.id.editComment);
        final EditText editPrice = findViewById(R.id.editPrice);
        final EditText editDate = findViewById(R.id.editDate);
        final EditText editMaker = findViewById(R.id.editMaker);
        final Intent intent = getIntent();
        String maker = intent.getStringExtra(GearAdapter.MAKER);
        String gear_date = intent.getStringExtra(GearAdapter.DATE);
        float price = intent.getFloatExtra(GearAdapter.PRICE, 0);
        final int position = intent.getIntExtra(GearAdapter.POSITION, 100);
        String string_price = Float.toString(price);
        final String comment = intent.getStringExtra(GearAdapter.COMMENT);
        String description = intent.getStringExtra(GearAdapter.DESCRIPTION);

        editDescription.setText(description);
        editComment.setText(comment);
        editDate.setText(gear_date);
        editMaker.setText(maker);
        editPrice.setText(string_price);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descriptionText = editDescription.getText().toString();
                String commentText = editComment.getText().toString();
                String priceText = editPrice.getText().toString();
                String makerText = editMaker.getText().toString();
                String dateText = editDate.getText().toString();
                // checking for empty sections, except for comments
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
                    } else if (!priceText.matches("[-+]?[0-9]*\\.?[0-9]+")) { // regex for floats values
                        Snackbar.make(view, "Price value is not correct", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (!dateText.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) { // regex for date YYYY-MM-DD
                        Snackbar.make(view, "Date is not correct (YYYY-MM-DD)", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Float ePrice = Float.parseFloat(priceText);
                        Intent i = new Intent(GearEditActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.putExtra(METHOD, 1);
                        i.putExtra(POS, position);
                        i.putExtra(EDITDESC, descriptionText);
                        i.putExtra(EDITPRICE, ePrice);
                        i.putExtra(EDITMAKER, makerText);
                        i.putExtra(EDITCOMMENT, commentText);
                        i.putExtra(EDITDATE, dateText);
                        startActivity(i);
                    }
                }
            }
        });

        //delete button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GearEditActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //skips onCreate method on MainActivity
                i.putExtra(METHOD, 2);
                i.putExtra(POS, position);
                startActivity(i);
            }
        });
    }
}