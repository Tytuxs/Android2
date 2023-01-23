package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

        Button buttonBROOM = findViewById(R.id.buttonBROOM);
        buttonBROOM.setOnClickListener(this);
        Button buttonPROOM = findViewById(R.id.buttonPROOM);
        buttonPROOM.setOnClickListener(this);
        Button buttonLROOMS = findViewById(R.id.buttonLROOMS);
        buttonLROOMS.setOnClickListener(this);
        Button buttonCROOM = findViewById(R.id.buttonCROMP);
        buttonCROOM.setOnClickListener(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonDeconnexion = findViewById(R.id.buttonDeconnexion);
        buttonDeconnexion.setOnClickListener(this);
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
        if(v == findViewById(R.id.buttonLROOMS)) {
            System.out.println("LROOMS");
            Intent intent = new Intent(this, ListeChambre.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);
        }
        if(v == findViewById(R.id.buttonCROMP)) {
            Intent intent = new Intent(this, AnnulerReservation.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);
        }
        if(v == findViewById(R.id.buttonDeconnexion)) {
            Socket s = socketHandler.getSocket();
            ObjectOutputStream oos = socketHandler.getOos();
            ObjectInputStream ois = socketHandler.getOis();

            try {
                oos.writeObject("Exit");
                s.close();
                oos.close();
                ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);

        }
    }
}