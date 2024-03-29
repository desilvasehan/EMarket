package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerStats extends AppCompatActivity {

    static String[] ItemName,ItemPrice,ItemQty,Supplier;
    ListView list;
    String phone;
    DatabaseReference reff;
    int n_child;
    Home nH;
    String value;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_stats);

        ItemName = new String[n_child+2];
        ItemPrice = new String[n_child+2];
        ItemQty = new String[n_child+2];

        nH = new Home();
        reff = FirebaseDatabase.getInstance().getReference().child("Items");

        //getting ItemList Running

        final String mail_address =  nH.getPersonEmail();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                n_child = (int)dataSnapshot.getChildrenCount();
                // System.out.println("Item Name: " + n_child);

                int val = 0;
                String IN,SQ,Sup,CA,sold_qty,Cus;
                for (int i=1;i<=n_child;i++){
                    IN = dataSnapshot.child(String.valueOf(i)).child("item_name").getValue().toString();
                    SQ = dataSnapshot.child(String.valueOf(i)).child("current_qyt").getValue().toString();
                    Sup = dataSnapshot.child(String.valueOf(i)).child("sup_email").getValue().toString();
                    Cus = dataSnapshot.child(String.valueOf(i)).child("usr_email").getValue().toString();
                    CA = dataSnapshot.child(String.valueOf(i)).child("stock_qyt").getValue().toString();
                    sold_qty = String.valueOf((Float.parseFloat(SQ)/(Float.parseFloat(CA)))*100) + "%";
                    //output checking
                    System.out.println(Sup);
                    System.out.println(mail_address);
                    /*System.out.println(Usermail.equals(mail_address));
                    System.out.println(IN);
                    System.out.println(IP);
                    System.out.println(SQ);
                    System.out.println(Sup);}*/

                    if (Sup.equals(mail_address)){
                        ItemName[val] = IN;
                        ItemPrice[val] = sold_qty;
                        ItemQty[val]= Cus;

                        System.out.println("Phone : : " +ItemQty[val]);
                       /* System.out.println("Item Name " +ItemName[val]);
                        System.out.println("Item Price " +ItemPrice[val]);
                        System.out.println("Item Quantity" +ItemQty[val]);
                        System.out.println("Sup Mail " +Supplier[val]);
                        //System.out.println("User Mail" +);
                        System.out.println("-----------------------------------------");
                        System.out.println("Value Changed");*/
                        //System.out.println("Item Name1: " + ItemName[0]);
                        val =val+1;
                    }
                    else{

                    }



                }
                //System.out.println("Item Name2: " + ItemName[0]);
                loadvalues();
            };

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        System.out.println("Item Name3: " + value  );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomerStats.this, BusinessHome.class));
        finish();
    }
    //loading values to listview
    public void loadvalues(){
        final CustomerListAdaptor adaptor = new CustomerListAdaptor(this,ItemName,ItemPrice,ItemQty);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter((CustomerListAdaptor) adaptor);
    }
}
