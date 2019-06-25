package com.example.e_market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellerHome extends AppCompatActivity {

    Button btn_items,btn_stats,btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        //switch to items intent
        btn_items = findViewById(R.id.btn_items);
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerHome.this, Items.class));
                finish();
            }
        });

        //swithch to stats intent
        btn_stats = findViewById(R.id.btn_statistics);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerHome.this, Business_Stats.class));
                finish();
            }
        });

        //swithch to stats intent
        btn_update  = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerHome.this, UpdateItems.class));
                finish();
            }
        });




    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SellerHome.this, BusinessLogin.class));
        finish();
    }


}
