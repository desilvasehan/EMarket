package com.example.e_market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out,search;
    ImageView photoIV;
    ImageButton imgButton;
    DatabaseReference reff;
    static String[] ItemName,ItemPrice,ItemQty,Supplier;
    ListView list;
    int n_child;
    Home nH;
    String value;



    public static String personEmail;

    public String getPersonEmail() {
        return personEmail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sign_out = findViewById(R.id.log_out);
        photoIV = findViewById(R.id.photo);
        imgButton = findViewById(R.id.business_work);
        search = findViewById(R.id.Search);

        nH = new Home();
        final String mail_address =  nH.getPersonEmail();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Home.this);
        if (acct != null) {
            /*String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();*/
            personEmail = acct.getEmail();

            //String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Glide.with(this).load(personPhoto).into(photoIV);

        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.workman);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        photoIV.setImageDrawable(roundedBitmapDrawable);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BusinessLogin.class));
                finish();
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Items");
/*
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        n_child = (int)dataSnapshot.getChildrenCount();
                        // System.out.println("Item Name: " + n_child);
                        ItemName = new String[n_child+2];
                        ItemPrice = new String[n_child+2];
                        ItemQty = new String[n_child+2];
                        Supplier = new String[n_child+2];

                        int val =0;
                        String IN,IP,SQ,Sup,Usermail;
                        for (int i=1;i<=n_child;i++){
                            IN = dataSnapshot.child(String.valueOf(i)).child("item_name").getValue().toString();
                            IP = dataSnapshot.child(String.valueOf(i)).child("item_price").getValue().toString();
                            SQ = dataSnapshot.child(String.valueOf(i)).child("stock_qyt").getValue().toString();
                            //Sup = dataSnapshot.child(String.valueOf(i)).child("sup_email").getValue().toString();
                            Usermail = dataSnapshot.child(String.valueOf(i)).child("usr_email").getValue().toString();

                            //output checking
                    /*{ System.out.println(Usermail);
                    System.out.println(mail_address);
                    System.out.println(Usermail.equals(mail_address));
                    System.out.println(IN);
                    System.out.println(IP);
                    System.out.println(SQ);
                    System.out.println(Sup);}

                            if (Usermail.equals(mail_address)){
                                ItemName[val] = IN;
                                ItemPrice[val] = IP;
                                ItemQty[val] =  SQ;
                                //Supplier[val] = Sup;

                       /* System.out.println("Item Name " +ItemName[val]);
                        System.out.println("Item Price " +ItemPrice[val]);
                        System.out.println("Item Quantity" +ItemQty[val]);
                        System.out.println("Sup Mail " +Supplier[val]);
                        //System.out.println("User Mail" +);
                        System.out.println("-----------------------------------------");
                        System.out.println("Value Changed");
                                System.out.println("Item Name1: " + ItemName[0]);

                                val =val+1;
                            }
                            else{

                            }



                        }
                        //System.out.println("Item Name2: " + ItemName[0]);
                        loadvalues();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
    }

    private void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Home.this, MainActivity.class));
                        finish();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Home.this, MainActivity.class));
        finish();
    }

    /*
    public void loadvalues(){
        final ItemListAdaptor adaptor = new ItemListAdaptor(this,ItemName,ItemPrice,ItemQty,Supplier);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter((ItemListAdaptor) adaptor);
    }
    */
}
