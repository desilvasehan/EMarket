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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Home extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    ImageView photoIV;
    ImageButton imgButton;



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
}
