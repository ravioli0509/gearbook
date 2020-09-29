package com.example.gearbook_nawalage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // initializing
    ListView listView;
    ArrayList<Gear> gear_list = new ArrayList<>();
    // using this format to parse string date to Date format
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    float total_price = 0; // used for total price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // on create creating the list, fab, and setting gear_list on adapter
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        TextView priceTotal = findViewById(R.id.price_total);
        GearAdapter gearAdapter = new GearAdapter(MainActivity.this);
        gearAdapter.setGearList(gear_list);
        listView.setAdapter(gearAdapter);
        gearAdapter.notifyDataSetChanged();
        priceTotal.setText("Total: $0.0");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // this onNewIntent is used to fire when GearEditRegistry is firing an intent as it ignores onCreate method with flags
        // if condition is 1, then the gear is updated, 2 for deletion
        super.onNewIntent(intent);
        int condition= intent.getIntExtra(GearEditActivity.METHOD, -1);
        int position = intent.getIntExtra(GearEditActivity.POS, -1);
        if ( condition == 1) {
            String eDesc = intent.getStringExtra(GearEditActivity.EDITDESC);
            String eMaker = intent.getStringExtra(GearEditActivity.EDITMAKER);
            String eComment = intent.getStringExtra(GearEditActivity.EDITCOMMENT);
            Float ePrice = intent.getFloatExtra(GearEditActivity.EDITPRICE, -1);
            String unparsedDate = intent.getStringExtra(GearEditActivity.EDITDATE);
            Gear editingObj = gear_list.get(position);
            editingObj.setDescription(eDesc);
            editingObj.setMaker(eMaker);
            editingObj.setComment(eComment);
            editingObj.setPrice(ePrice);
            // try and catch for parsing Date
            try {
                Date eDate = format.parse(unparsedDate);
                editingObj.setDate(eDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GearAdapter gearAdapter = new GearAdapter(MainActivity.this);
            gearAdapter.setGearList(gear_list);
            listView.setAdapter(gearAdapter);
            float total_price = getSum(gear_list);
            TextView priceTotal = findViewById(R.id.price_total);
            priceTotal.setText("Total: $"+ total_price);
            gearAdapter.notifyDataSetChanged();
        } else if ( condition == 2 ){
            gear_list.remove(position);
            GearAdapter gearAdapter = new GearAdapter(MainActivity.this);
            gearAdapter.setGearList(gear_list);
            listView.setAdapter(gearAdapter);
            total_price = getSum(gear_list);
            TextView priceTotal = findViewById(R.id.price_total);
            priceTotal.setText("Total: $"+ total_price);
            gearAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View button_id) {
        // onclick for firing a Gear Registry activity
        // When it comes back, onActivityResult will run with intent data
        Intent intent = new Intent(this, GearRegistryActivity.class);
        this.startActivityForResult(intent, 1);
    }

    public float getSum(ArrayList<Gear> gear_list) {
        // method to return total price using the current gear_list
        float sum = 0;
        for (int i=0; i < gear_list.size(); i++) {
            sum = sum + gear_list.get(i).getPrice();
        }
        return sum;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Method to Add New Gear, using the intent came back from Gear Registry Activity.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                Gear gear = new Gear();
                String description = data.getStringExtra("result_description");
                float price = data.getFloatExtra("result_price", 0);
                String comment = data.getStringExtra("result_comment");
                String maker = data.getStringExtra("result_maker");
                String date_string = data.getStringExtra("result_date");
                try {
                    Date date = format.parse(date_string);
                    gear.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gear.setDescription(description);
                gear.setPrice(price);
                gear.setMaker(maker);
                gear.setComment(comment);
                gear_list.add(gear);
                total_price = getSum(gear_list);
                TextView priceTotal = findViewById(R.id.price_total);
                priceTotal.setText("Total: $"+ total_price);
                GearAdapter gearAdapter = new GearAdapter(MainActivity.this);
                gearAdapter.setGearList(gear_list);
                listView.setAdapter(gearAdapter);
                gearAdapter.notifyDataSetChanged();
            }
        }
    }
}