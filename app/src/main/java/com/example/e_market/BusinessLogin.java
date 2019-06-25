package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusinessLogin extends AppCompatActivity {

    DatabaseReference reff;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_login);
        TextView TV_email =  findViewById(R.id.email_tv);
        Home nH =  new Home();
        Button logbtn = findViewById(R.id.log_in);
        final EditText passcode = findViewById(R.id.access_code);
        ImageButton imageButton1 = findViewById(R.id.register);
        String mail_address =  nH.getPersonEmail();
        TV_email.setText(mail_address);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");

       imageButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessLogin.this,Register.class));
                finish();
            }
        });

       logbtn.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Home nH =  new Home();
               String mail_address =  nH.getPersonEmail();
               reff = FirebaseDatabase.getInstance().getReference().child("Member").child("1");

               reff.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String accesscodedb =dataSnapshot.child("passcode").getValue().toString();
                        String accesscodeusr = passcode.getText().toString().trim();

                        if(accesscodedb.equals(accesscodeusr)) {
                            startActivity(new Intent(BusinessLogin.this, SellerHome.class));
                            finish();
                        }
                        else{
                            Toast.makeText(BusinessLogin.this,"Error!",Toast.LENGTH_LONG).show();
                        }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       });
    }
}
