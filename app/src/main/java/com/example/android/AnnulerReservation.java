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

public class AnnulerReservation extends AppCompatActivity implements View.OnClickListener {

    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_annuler_reservation);

        socketHandler = getIntent().getParcelableExtra("socket");
        oos = socketHandler.getOos();
        ois = socketHandler.getOis();

        Button annuler = this.findViewById(R.id.buttonConfirmerAnnulation);
        annuler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.buttonConfirmerAnnulation)) {
            try {
                EditText id = this.findViewById(R.id.editTextIdReservation);
                oos.writeObject("CROOM");

                oos.writeObject(id.getText().toString());

                String confirmation = (String) ois.readObject();

                if (confirmation.equals("OK")) {
                    System.out.println("OK + " + confirmation);
                } else {
                    System.out.println("NOK + " + confirmation);
                }

                Intent intent = new Intent(this, MenuROMP.class);
                intent.putExtra("socket",socketHandler);
                finish();
                startActivity(intent);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}