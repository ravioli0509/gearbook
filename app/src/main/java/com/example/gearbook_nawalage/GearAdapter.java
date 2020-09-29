package com.example.gearbook_nawalage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GearAdapter extends BaseAdapter {
    public static final String PRICE = "com.example.gearbook_nawalage.example.PRICE";
    public static final String MAKER = "com.example.gearbook_nawalage.example.MAKER";
    public static final String DATE = "com.example.gearbook_nawalage.example.DATE";
    public static final String COMMENT = "com.example.gearbook_nawalage.example.COMMENT";
    public static final String DESCRIPTION = "com.example.gearbook_nawalage.example.DESCRIPTION";
    public static final String POSITION = "com.example.gearbook_nawalage.example.POSITION";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Context context;
    LayoutInflater layoutInflater = null;
    // Layout inflater is used to instantiate a layout into its corresponding view objects. If multiple object need to be seen
    // on the view, we make a custom Adapter.
    ArrayList<Gear> gearList;

    public GearAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //setting the list
    public void setGearList(ArrayList<Gear> gearList){
        this.gearList = gearList;
    }

    // returning list size
    @Override
    public int getCount() {
        return gearList.size();
    }

    // returning the position of the item from the list
    @Override
    public Object getItem(int position) {
        return gearList.get(position);
    }

    // returning the item id
    @Override
    public long getItemId(int position) {
        return gearList.get(position).getId();
    }

    // method for getting list view
    @Override
    public View getView(final int position, View gearView, ViewGroup parent) {
        gearView = layoutInflater.inflate(R.layout.gearrow, parent, false);
        // Only Description, Price, and Date set in the View.

        ((TextView)gearView.findViewById(R.id.description)).setText(gearList.get(position).getDescription()); //Description
        String dateTime = dateFormat.format(gearList.get(position).getDate());
        ((TextView)gearView.findViewById(R.id.date)).setText(dateTime); // Parsed date
        ((TextView)gearView.findViewById(R.id.price)).setText(String.valueOf(gearList.get(position).getPrice())); //Parsed Price

        // On click shows the detail of the clicked gear item (NOT LONG CLICK)
        gearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GearDetailActivity.class);
                String maker = gearList.get(position).getMaker();
                Date date = gearList.get(position).getDate();
                float price = gearList.get(position).getPrice();
                String description = gearList.get(position).getDescription();
                String comment = gearList.get(position).getComment();
                String dateTime = dateFormat.format(date);
                intent.putExtra(PRICE, price);
                intent.putExtra(DATE, dateTime);
                intent.putExtra(DESCRIPTION, description);
                intent.putExtra(MAKER, maker);
                intent.putExtra(COMMENT, comment);
                context.startActivity(intent);
            }
        });

        // Long click shows the editing page of the gear item, also able to delete.
        gearView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, GearEditActivity.class);
                String maker = gearList.get(position).getMaker();
                Date date = gearList.get(position).getDate();
                float price = gearList.get(position).getPrice();
                String description = gearList.get(position).getDescription();
                String comment = gearList.get(position).getComment();
                String dateTime = dateFormat.format(date);
                intent.putExtra(POSITION, position);
                intent.putExtra(PRICE, price);
                intent.putExtra(DATE, dateTime);
                intent.putExtra(DESCRIPTION, description);
                intent.putExtra(MAKER, maker);
                intent.putExtra(COMMENT, comment);
                context.startActivity(intent);
                return false;
            }
        });
        return gearView;
    }
}
