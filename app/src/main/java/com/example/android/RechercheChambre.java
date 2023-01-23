package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import Classe.ReserActCha;

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


        oos.writeObject("BROOM");

        ReserActCha resa = new ReserActCha() ;

        resa.set_categorie(spinnerCategorie.getSelectedItem().toString());
        resa.set_typeCha(spinnerNbPersonne.getSelectedItem().toString());
        resa.set_nbNuits(Integer.parseInt(nbNuits.getText().toString()));
        resa.set_date(date.getText().toString());
        resa.set_persRef(nom.getText().toString());


    }
}