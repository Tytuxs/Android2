package com.example.android;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import Classe.Chambre;

public class ResultatRecherche extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Chambre> list;
    SocketHandler socketHandler;
    ObjectOutputStream oos;
    ObjectInputStream ois;
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
            for (int i=0; i<list.size();i++) {
                System.out.println(list.get(i).get_numeroChambre());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        recyclerView = this.findViewById(R.id.recyclerViewListChambre);
    }
}