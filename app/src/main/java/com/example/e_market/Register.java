package com.example.e_market;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity{

    EditText passcode1,passcode2,phone_number;
    TextView txt_email;
    Switch acc_type;
    Button reg_button;
    DatabaseReference reff;
    new_Member member;
    long maxval;//changed
    Home nH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_button = findViewById(R.id.reg_button);
        txt_email = (TextView)findViewById(R.id.textView2);
        phone_number = (EditText)findViewById(R.id.editph);
        passcode1 = (EditText)findViewById(R.id.editText2);
        acc_type = findViewById(R.id.switch1);

        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        member = new new_Member();
        nH = new Home();

        String mail_address =  nH.getPersonEmail();
        txt_email.setText(mail_address);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maxvalGen();

                member.setEmail(txt_email.getText().toString().trim());
                member.setPhone(phone_number.getText().toString().trim());
                member.setPasscode(passcode1.getText().toString().trim());

                reff.child(String.valueOf(maxval+1)).setValue(member);

                startActivity(new Intent(Register.this,BusinessLogin.class));
                finish();
            }
        });



    }

    public  void  passmatch(){
        passcode1 = findViewById(R.id.editText2);
        passcode2 = findViewById(R.id.editText3);

        if(!(passcode1.equals(passcode2))){
            Toast.makeText(Register.this,"Passcode Does not match!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(Register.this,"Passcode successfully match!",Toast.LENGTH_SHORT).show();
        }
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


}