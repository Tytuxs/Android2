package com.example.android;

import Classe.Chambre;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import Classe.ReserActCha;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class RechercheChambre extends AppCompatActivity implements View.OnClickListener{
    TextView nom = this.findViewById(R.id.editTextName);
    TextView date = this.findViewById(R.id.editTextDate);
    Spinner spinnerNbPersonne = this.findViewById(R.id.spinnerNbPersonne);
    Spinner spinnerCategorie = this.findViewById(R.id.spinnerCategorie);
    TextView nbNuits = this.findViewById(R.id.editTextTextNbNuits);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_chambre);

        String[] dataNbPersonne = {"Simple", "Double", "Familiale"};
        ArrayAdapter<String> adapternbPersonne = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataNbPersonne);
        spinnerNbPersonne.setAdapter(adapternbPersonne);

        String[] dataCategorie = {"Motel", "Village"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataCategorie);
        spinnerCategorie.setAdapter(adapterCategorie);

        Button buttonRecherche = this.findViewById(R.id.buttonConnexion);
        buttonRecherche.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        try {
            Intent intent = new Intent(this, RechercheChambre.class);
            SocketHandler s = intent.getParcelableExtra("socket");
            ObjectOutputStream oos = s.getOos();
            ObjectInputStream ois = s.getOis();

            oos.writeObject("BROOM");
            ReserActCha resa = new ReserActCha() ;

            resa.set_categorie(spinnerCategorie.getSelectedItem().toString());
            resa.set_typeCha(spinnerNbPersonne.getSelectedItem().toString());
            resa.set_nbNuits(Integer.parseInt(nbNuits.getText().toString()));
            resa.set_date(date.getText().toString());
            resa.set_persRef(nom.getText().toString());

            oos.writeObject(resa);

            Chambre chambre;
            int compteur = 0;//sert a savoir si on a au moins un resultat
            while (true) {
                chambre = (Chambre) ois.readObject();
                if (chambre == null)
                    break;
                else {
                    Vector v = new Vector();
                    /*v.add(chambre.get_numeroChambre());
                    v.add(chambre.get_prixHTVA());
                    JTable_Affichage.addRow(v);
                    compteur++;*/
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}