package com.example.gearbook_nawalage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GearDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_detail);

        Intent intent = getIntent();
        String maker = intent.getStringExtra(GearAdapter.MAKER);
        String gear_date = intent.getStringExtra(GearAdapter.DATE);
        float price = intent.getFloatExtra(GearAdapter.PRICE, 0);

        String comment = intent.getStringExtra(GearAdapter.COMMENT);
        String description = intent.getStringExtra(GearAdapter.DESCRIPTION);

        TextView makerView = (TextView) findViewById(R.id.detail_gear_maker);
        TextView priceView = findViewById(R.id.detail_gear_price);
        TextView dateView = (TextView) findViewById(R.id.detail_gear_date);
        TextView commentView = (TextView) findViewById(R.id.detail_gear_comment);
        TextView descriptionView = (TextView) findViewById(R.id.detail_gear_description);

        makerView.setText(maker);
        priceView.setText("$"+ price);
        dateView.setText(gear_date);
        commentView.setText(comment);
        descriptionView.setText(description);
    }
}