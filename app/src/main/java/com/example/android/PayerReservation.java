package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Classe.Chambre;

public class PayerReservation extends AppCompatActivity implements View.OnClickListener {

    SocketHandler socketHandler;
    EditText nomClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_payer_reservation);

        nomClient = this.findViewById(R.id.editTextClient);

        Button buttonrechercheresa = this.findViewById(R.id.buttonRechercherResa);
        buttonrechercheresa.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == this.findViewById(R.id.buttonRechercherResa)) {
            try {
                socketHandler = getIntent().getParcelableExtra("socket");
                ObjectOutputStream oos = socketHandler.getOos();
                ObjectInputStream ois = socketHandler.getOis();

                oos.writeObject("PROOM");
                oos.writeObject(nomClient.getText().toString());

                Intent intent = new Intent(this, ListeReservationAPayer.class);
                intent.putExtra("socket",socketHandler);
                finish();
                startActivity(intent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}