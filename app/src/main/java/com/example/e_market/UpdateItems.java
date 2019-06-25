package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UpdateItems extends AppCompatActivity{

    static String[] ItemName;
    Button btnUpdate;
    DatabaseReference reff;
    String usermail,up_qyt;
    EditText quantity;
    Home nH;
    Item item;
    TransactionHistory transactionHistory;
    int n_child;
    long maxval;//changed
    int n_childrens;

    private Spinner spinner1;


    EditText itemname,itemprice,stockqty,sup_email;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_items);
        nH = new Home();
        String mail_address =  nH.getPersonEmail();
        usermail = mail_address;

        reff = FirebaseDatabase.getInstance().getReference().child("Items");
        item = new Item();

        itemname = (EditText)findViewById(R.id.txt_name);
        itemprice = (EditText)findViewById(R.id.txt_price);
        stockqty = (EditText)findViewById(R.id.txt_quantity);
        sup_email = (EditText)findViewById(R.id.txt_supemail);




        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxvalGen();
                item.setItem_name(itemname.getText().toString().trim());
                item.setItem_price(itemprice.getText().toString().trim());
                item.setStock_qyt(stockqty.getText().toString().trim());
                item.setCurrent_qyt(stockqty.getText().toString().trim());
                item.setSup_email(sup_email.getText().toString().trim());
                item.setUsr_email(usermail);

                reff.child(String.valueOf(maxval+1)).setValue(item);
                Toast.makeText(UpdateItems.this,"Item Added Successfully!",Toast.LENGTH_SHORT).show();
            }
        });

        ItemName = new String[n_child+2];

        reff.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_child = (int) dataSnapshot.getChildrenCount();
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
                System.out.println("Item Name2: " + ItemName[0]);
                addItemsOnSpinner2(ItemName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addListenerOnSpinnerItemSelection();

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        quantity = findViewById(R.id.txt_update_quantity);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        transactionHistory = new TransactionHistory();
        final String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        System.out.println("Current Time : " + today);
        reff = FirebaseDatabase.getInstance().getReference().child("transactionHistory");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_childrens = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String ItemN = String.valueOf(spinner1.getSelectedItem());
                System.out.println("Item Name: " +ItemN);
                System.out.println("User Mail: " + usermail);
                System.out.println("Quantity : "+ quantity.getText().toString().trim());
                transactionHistory.setItem_name(String.valueOf(spinner1.getSelectedItem()));
                transactionHistory.setUsr_email(usermail.toString().trim());
                transactionHistory.setSold_qyt(quantity.getText().toString().trim());
                transactionHistory.setAdded_date(today.toString().trim());
                reff.child(String.valueOf(n_childrens+1)).setValue(transactionHistory);
                updateCurrent();
                startActivity(new Intent(UpdateItems.this,BusinessHome.class));
                finish();
            }

        });
    }

    public void maxvalGen(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxval = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateItems.this, BusinessHome.class));
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

    public void updateCurrent(){
        reff = FirebaseDatabase.getInstance().getReference().child("items");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_child = (int) dataSnapshot.getChildrenCount();
                String itemname,Usermail,sold_qty;
                int qtty;
                for (int i = 1; i <= n_child; i++) {
                    itemname = dataSnapshot.child(String.valueOf(i)).child("item_name").getValue().toString();
                    Usermail = dataSnapshot.child(String.valueOf(i)).child("usr_email").getValue().toString();
                    qtty = Integer.parseInt(dataSnapshot.child(String.valueOf(i)).child("stock_qyt").getValue().toString());
                    sold_qty = String.valueOf(qtty -  Integer.parseInt(quantity.getText().toString().trim()));
                    if ((Usermail.equals(usermail))&&(itemname.equals(String.valueOf(spinner1.getSelectedItem())))) {
                        updateDB(String.valueOf(i),sold_qty);
                        System.out.println("Done!");
                        break;
                    } else {

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void updateDB(String key,String newqty){
        reff.child("Items").child(key).removeValue();
        //reff.child("items").child(key).child("stock_qyt").setValue(newqty);
    }
}
