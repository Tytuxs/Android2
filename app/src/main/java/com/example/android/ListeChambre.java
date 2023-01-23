package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import Classe.ReserActCha;

public class ListeChambre extends AppCompatActivity implements View.OnClickListener{

    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ArrayList<String> liste = new ArrayList<>();
    ListView listViewListe;
    String objet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_liste_chambre);

        try {
            System.out.println("ListeChambre");
            socketHandler = getIntent().getParcelableExtra("socket");

            oos = socketHandler.getOos();
            ois = socketHandler.getOis();

            oos.writeObject("LROOMS");

            ReserActCha reservation;
            while (true) {
                reservation = (ReserActCha) ois.readObject();
                if(reservation==null)
                    break;
                else {
                    float restant = reservation.get_prixCha() - reservation.get_dejaPaye();
                    if (restant <= 0) {
                        restant = 0;
                    }

                    String objet = "id: " + reservation.get_id() + "-NumChambre: " + reservation.get_numChambre()
                            + "-Persref: " + reservation.get_persRef() + "-date: " + reservation.get_date()
                            + "-restant: " + restant;
                    liste.add(objet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.listViewListe = this.findViewById(R.id.ListViewChambre);
        this.listViewListe.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,liste);
        listViewListe.setAdapter(arrayAdapter);

        this.listViewListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objet = (String) listViewListe.getItemAtPosition(i);
            }
        });

        Button buttonQuitter = this.findViewById(R.id.buttonQuitter);
        buttonQuitter.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view == this.findViewById(R.id.buttonQuitter)) {

            Intent intent = new Intent(this, MenuROMP.class);
            intent.putExtra("socket",socketHandler);
            finish();
            startActivity(intent);

        }
    }
}