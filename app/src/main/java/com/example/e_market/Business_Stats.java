package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Business_Stats extends AppCompatActivity {

    PieChart pieChart;
    String usermail;
    String[] ItemName;
    DatabaseReference reff;
    int n_child;
    private Spinner spinner1;
    final List<PieEntry> pie_values = new ArrayList<>();



    Home nH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        nH = new Home();
        usermail = nH.getPersonEmail();
        reff = FirebaseDatabase.getInstance().getReference().child("Items");

        reff.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_child = (int) dataSnapshot.getChildrenCount();
                ItemName = new String[n_child+2];

                int val = 0;
                String IN,Usermail;
                for (int i = 1; i <= n_child; i++) {
                    IN = dataSnapshot.child(String.valueOf(i)).child("item_name").getValue().toString();
                    Usermail = dataSnapshot.child(String.valueOf(i)).child("usr_email").getValue().toString();

                    if (Usermail.equals(usermail)) {
                        ItemName[val] = IN;
                        val = val + 1;
                    } else {
                    }
                }
                addItemsOnSpinner2(ItemName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pieChart = findViewById(R.id.stat_pie);
        pieChart.setUsePercentValues(true);



        if(pieChart.callOnClick()){
            pieChart.refreshDrawableState();
        }

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_child = (int) dataSnapshot.getChildrenCount();
                String itemname,Usermail,sold_qty,stock,c_stock;
                for (int i = 1; i <= n_child; i++) {
                    itemname = dataSnapshot.child(String.valueOf(i)).child("item_name").getValue().toString();
                    Usermail = dataSnapshot.child(String.valueOf(i)).child("usr_email").getValue().toString();
                    stock = dataSnapshot.child(String.valueOf(i)).child("stock_qyt").getValue().toString();
                    c_stock = dataSnapshot.child(String.valueOf(i)).child("current_qyt").getValue().toString();
                    sold_qty = String.valueOf(  Integer.parseInt(stock) - Integer.parseInt(c_stock) );
                    System.out.println("Test Pass : "+ sold_qty);
                    if ((Usermail.equals(usermail))&&(itemname.equals(String.valueOf(spinner1.getSelectedItem())))) {
                        loadPie(itemname,sold_qty,c_stock);
                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void loadPie(String itemname,String sold_qty,String c_stock){
        pie_values.add(new PieEntry(Integer.valueOf(sold_qty),"Sold"));
        pie_values.add(new PieEntry(Integer.valueOf(c_stock),"Available"));
        PieDataSet pieDataSet = new PieDataSet(pie_values,itemname);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Business_Stats.this, BusinessHome.class));
        finish();
    }

    public void addItemsOnSpinner2(String[] addval) {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        for(int i=0;i<addval.length;i++){
            list.add(addval[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
