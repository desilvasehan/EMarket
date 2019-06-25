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
    int n_child;
    static String CustomerType;

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
               final String mail_address =  nH.getPersonEmail();
               reff = FirebaseDatabase.getInstance().getReference().child("Member");

               reff.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       n_child = (int)dataSnapshot.getChildrenCount();

                       for (int i=1;i<=n_child;i++){
                           String accesscodedb = dataSnapshot.child(String.valueOf(i)).child("passcode").getValue().toString();
                           String accessmail = dataSnapshot.child(String.valueOf(i)).child("email").getValue().toString();
                           System.out.println("Email : "+ accessmail);
                           System.out.println("AccessCode : " + accesscodedb);
                           String accesscodeusr = passcode.getText().toString().trim();

                           if(accesscodedb.equals(accesscodeusr)&&(accessmail.equals(mail_address))) {
                               CustomerType = dataSnapshot.child(String.valueOf(i)).child("acc_type").getValue().toString();
                               System.out.println("Customer Type : " + CustomerType);
                               Toast.makeText(BusinessLogin.this,"Successfull Login!",Toast.LENGTH_LONG).show();
                               startActivity(new Intent(BusinessLogin.this, BusinessHome.class));
                               finish();
                           }
                           else{

                           }
                       }
                   };

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       });
    }
}
