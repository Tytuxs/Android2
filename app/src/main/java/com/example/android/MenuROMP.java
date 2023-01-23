package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class MenuROMP extends AppCompatActivity implements View.OnClickListener{

    SocketHandler socketHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_menu_romp);
        socketHandler = getIntent().getParcelableExtra("socket");
        System.out.println(socketHandler);
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.buttonBROOM)) {
            Intent intent = new Intent(this, RechercheChambre.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);
        }
        if(v == findViewById(R.id.buttonPROOM)) {
            Intent intent = new Intent(this, PayerReservation.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);
        }
    }
}