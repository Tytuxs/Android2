package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Classe.Chambre;
import Classe.ReserActCha;

public class ResultatRecherche extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Chambre> list = new ArrayList<>();
    private ArrayList<String> liste;
    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ListView listViewListeChambre;
    String objet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_resultat_recherche);

        try {
            socketHandler = getIntent().getParcelableExtra("socket");
            System.out.println(socketHandler);
            oos = socketHandler.getOos();
            ois = socketHandler.getOis();

            Chambre chambre;
            int compteur = 0;//sert a savoir si on a au moins un resultat
            while (true) {
                chambre = (Chambre) ois.readObject();
                if (chambre == null)
                    break;
                else {
                    list.add(chambre);
                }
            }
            if(compteur == 0) {
                oos.writeObject("Aucune");
            }
            else {
                oos.writeObject("OK");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        liste = new ArrayList<>();

        for(int i =0; i<list.size();i++) {
            String objet = "NumChambre: " + String.valueOf(list.get(i).get_numeroChambre()) + " - Prix: " + String.valueOf(list.get(i).get_prixHTVA());
            liste.add(objet);
        }

        this.listViewListeChambre = findViewById(R.id.listViewListeChambre);
        this.listViewListeChambre.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,liste);
        listViewListeChambre.setAdapter(arrayAdapter);
        this.listViewListeChambre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*CheckedTextView v = (CheckedTextView) view;
                Chambre chambre = (Chambre) listViewListeChambre.getItemAtPosition(i);*/
                objet = (String) listViewListeChambre.getItemAtPosition(i);
            }
        });

        Button buttonReserver = this.findViewById(R.id.buttonReserver);
        buttonReserver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.buttonReserver)) {
            Chambre chambre = new Chambre();

            for(int i =0; i<list.size();i++) {
                String objetAComp = "NumChambre: " + String.valueOf(list.get(i).get_numeroChambre()) + " - Prix: " + String.valueOf(list.get(i).get_prixHTVA());
                if(objet.equals(objetAComp)) {
                    System.out.println("trouve dans liste");
                    chambre.set_numeroChambre(list.get(i).get_numeroChambre());
                    chambre.set_prixHTVA(list.get(i).get_prixHTVA());
                    break;
                }
            }

            try {
                oos.writeObject(chambre);
                String confirmation = (String) ois.readObject();

                if (confirmation.equals("NOK")) {
                    System.out.println("NOK");
                } else {
                    System.out.println("OK");
                    ReserActCha reservation = (ReserActCha) ois.readObject();
                    System.out.println(reservation);
                }

                Intent intent = new Intent(this, RechercheChambre.class);
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