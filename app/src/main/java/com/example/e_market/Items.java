package com.example.e_market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Items.this, SellerHome.class));
        finish();
    }


}
