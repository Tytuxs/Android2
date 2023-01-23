package com.example.android;

import Classe.Chambre;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import Classe.ReserActCha;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class RechercheChambre extends AppCompatActivity implements View.OnClickListener{

    Spinner spinnerNbPersonne;
    Spinner spinnerCategorie;
    SocketHandler socketHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_recherche_chambre);

        spinnerNbPersonne = this.findViewById(R.id.spinnerNbPersonne);
        spinnerCategorie = this.findViewById(R.id.spinnerCategorie);
        socketHandler = getIntent().getParcelableExtra("socket");

        String[] dataNbPersonne = {"Simple", "Double", "Familiale"};
        ArrayAdapter<String> adapternbPersonne = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataNbPersonne);
        spinnerNbPersonne.setAdapter(adapternbPersonne);

        String[] dataCategorie = {"Motel", "Village"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataCategorie);
        spinnerCategorie.setAdapter(adapterCategorie);

        Button buttonRecherche = this.findViewById(R.id.buttonRecherche);
        buttonRecherche.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view == this.findViewById(R.id.buttonRecherche)) {
            try {
                System.out.println(socketHandler);
                ObjectOutputStream oos = socketHandler.getOos();
                ObjectInputStream ois = socketHandler.getOis();

                TextView nom = this.findViewById(R.id.editTextName);
                TextView date = this.findViewById(R.id.editTextDate);
                TextView nbNuits = this.findViewById(R.id.editTextTextNbNuits);

                oos.writeObject("BROOM");
                ReserActCha resa = new ReserActCha();

                resa.set_categorie(spinnerCategorie.getSelectedItem().toString());
                resa.set_typeCha(spinnerNbPersonne.getSelectedItem().toString());
                resa.set_nbNuits(Integer.parseInt(nbNuits.getText().toString()));
                resa.set_date(date.getText().toString());
                resa.set_persRef(nom.getText().toString());

                System.out.println("ICIIIIIIIIIIIIIII");
                System.out.println(resa.get_categorie());
                oos.writeObject(resa);

                Intent intent = new Intent(this, ResultatRecherche.class);
                System.out.println("Avant" + socketHandler);

                intent.putExtra("socket",socketHandler);
                System.out.println("Après" + socketHandler);

                finish();
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}