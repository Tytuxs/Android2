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

import Classe.ReserActCha;

public class ValiderPaiement extends AppCompatActivity implements View.OnClickListener {

    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_valider_paiement);

        Button buttonPayer = this.findViewById(R.id.buttonPayerResaFinal);
        buttonPayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.buttonPayerResaFinal)) {
            try {
                socketHandler = getIntent().getParcelableExtra("socket");
                oos = socketHandler.getOos();
                ois = socketHandler.getOis();

                EditText editTextCB = this.findViewById(R.id.editTextCB);
                EditText editTextCBMDP = this.findViewById(R.id.editTextCBMDP);
                EditText editTextMontant = this.findViewById(R.id.editTextMontant);
                oos.writeObject(Float.parseFloat(String.valueOf(editTextMontant.getText())));
                oos.writeObject(editTextCB.getText().toString());
                oos.writeObject(editTextCBMDP.getText().toString());

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

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}