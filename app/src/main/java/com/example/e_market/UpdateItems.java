package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UpdateItems extends AppCompatActivity {

    DatabaseReference reff;
    String usermail;
    Home nH;
    Item item;
    long maxval;//changed


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
                item.setSup_email(sup_email.getText().toString().trim());
                item.setUsr_email(usermail);

                reff.child(String.valueOf(maxval+1)).setValue(item);
                Toast.makeText(UpdateItems.this,"Item Added Successfully!",Toast.LENGTH_SHORT).show();

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

}
