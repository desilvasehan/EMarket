package com.example.e_market;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdaptor extends ArrayAdapter<String>  {


        private final Activity context;
        private final String[] ItemName;
        private final String[] ItemPrice;
        private final String[] ItemQty;
        private final String[] Supplier;


        public ItemListAdaptor(Activity context, String[] ItemName,String[] ItemPrice,String[] ItemQty,String[] Supplier) {
            super(context, R.layout.itemlist,ItemName);
            this.context = context;
            this.ItemName = ItemName;
            this.ItemPrice = ItemPrice;
            this.ItemQty = ItemQty;
            this.Supplier = Supplier;
        }

        public View getView(int position,View view,ViewGroup parent) {
                LayoutInflater inflater=context.getLayoutInflater();
                View rowView=inflater.inflate(R.layout.itemlist, null,true);
                TextView txt_IN = (TextView) rowView.findViewById(R.id.ItemName);
                TextView txt_IP = (TextView)rowView.findViewById(R.id.ItemPrice);
                TextView txt_Qty = (TextView) rowView.findViewById(R.id.Quantity);
                TextView txt_SE = (TextView) rowView.findViewById(R.id.Suppliermail);
                txt_IN.setText(ItemName[position]);
                txt_IP.setText(ItemPrice[position]);
                txt_Qty.setText(ItemQty[position]);
                txt_SE.setText(Supplier[position]);
                return rowView;
        }
}
