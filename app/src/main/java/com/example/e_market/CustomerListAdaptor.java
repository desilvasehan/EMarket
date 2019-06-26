package com.example.e_market;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.utils.ColorTemplate;

public class CustomerListAdaptor  extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] ItemName;
        private final String[] ItemPrice;
        private final String[] ItemQty;

        public CustomerListAdaptor(Activity context, String[] ItemName,String[] AvailableQty,String[] Phone) {
            super(context, R.layout.itemlist,ItemName);
            this.context = context;
            this.ItemName = ItemName;
            this.ItemPrice = AvailableQty;
            this.ItemQty = Phone;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.customerlist, null,true);
            TextView txt_IN = (TextView) rowView.findViewById(R.id.ItemName);
            TextView txt_IP = (TextView)rowView.findViewById(R.id.Items_Available);
            TextView txt_Qty = (TextView) rowView.findViewById(R.id.Phone_number);
            txt_IN.setText(ItemName[position]);
            txt_IP.setText(ItemPrice[position]);
            txt_Qty.setText(ItemQty[position]);
            return rowView;
        }
}
