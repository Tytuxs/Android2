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

import Classe.Chambre;
import Classe.ReserActCha;

public class ListeReservationAPayer extends AppCompatActivity implements View.OnClickListener {

    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ListView listViewListeResa;
    private ArrayList<ReserActCha> list = new ArrayList<>();
    private ArrayList<String> listeString = new ArrayList<>();
    private ArrayList<Float> listerestant = new ArrayList<>();
    String objet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_liste_reservation_apayer);

        try {
            socketHandler = getIntent().getParcelableExtra("socket");
            oos = socketHandler.getOos();
            ois = socketHandler.getOis();

            ReserActCha reservation;
            while (true) {
                reservation = (ReserActCha) ois.readObject();
                if (reservation == null)
                    break;
                else {
                    list.add(reservation);
                    float restant = reservation.get_prixCha() - reservation.get_dejaPaye();
                    if (restant <= 0) {
                        restant = 0;
                    }
                    listerestant.add(restant);

                    String resa = "id: " + reservation.get_id() + "-NumChambre: " + reservation.get_numChambre()
                            + "-PrixChambre: " + reservation.get_prixCha() + "-PersRef: " + reservation.get_persRef()
                            + "-PrixRestant: " + restant;

                    listeString.add(resa);
                }
            }
            if(list.size() <= 0)
                oos.writeObject("NOK");
            else
                oos.writeObject("OK");

            this.listViewListeResa = this.findViewById(R.id.ListViewResa);
            this.listViewListeResa.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,listeString);
            listViewListeResa.setAdapter(arrayAdapter);

            this.listViewListeResa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    objet = (String) listViewListeResa.getItemAtPosition(i);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Button buttonPayer = this.findViewById(R.id.buttonPayerResa);
        buttonPayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.buttonPayerResa)) {
            Chambre chambre = new Chambre();

            try {
                for(int i =0; i<list.size();i++) {
                    String resa = "id: " + list.get(i).get_id() + "-NumChambre: " + list.get(i).get_numChambre()
                            + "-PrixChambre: " + list.get(i).get_prixCha() + "-PersRef: " + list.get(i).get_persRef()
                            + "-PrixRestant: " + listerestant.get(i);

                    if(objet.equals(resa)) {
                        System.out.println("trouve dans liste");
                        oos.writeObject(String.valueOf(list.get(i).get_id()));
                        break;
                    }
                }

                Intent intent = new Intent(this, ValiderPaiement.class);
                intent.putExtra("socket",socketHandler);
                finish();
                startActivity(intent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}