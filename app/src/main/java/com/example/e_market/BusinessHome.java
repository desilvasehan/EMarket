package com.example.e_market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusinessHome extends AppCompatActivity {

    Button btn_items,btn_stats,btn_update,btn_cusstats;
    Register r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        btn_cusstats = findViewById(R.id.btn_customerstats);
        r = new Register();
/*        if (r.acc_type.equals("Supplier")){
            btn_cusstats.setVisibility(View.VISIBLE);
        }
        else{
            btn_cusstats.setVisibility(View.INVISIBLE);
        }*/
        //switch to items intent
        btn_items = findViewById(R.id.btn_items);
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessHome.this, Items.class));
                finish();
            }
        });

        //swithch to stats intent
        btn_stats = findViewById(R.id.btn_statistics);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessHome.this, Business_Stats.class));
                finish();
            }
        });

        //swithch to stats intent
        btn_update  = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessHome.this, UpdateItems.class));
                finish();
            }
        });




    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BusinessHome.this, BusinessLogin.class));
        finish();
    }


}
