package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RechercheChambre extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_chambre);

        TextView nom = this.findViewById(R.id.editTextName);
        TextView date = this.findViewById(R.id.editTextDate);
        Spinner spinnerNbPersonne = this.findViewById(R.id.spinnerNbPersonne);
        Spinner spinnerCategorie = this.findViewById(R.id.spinnerCategorie);
        TextView nbNuits = this.findViewById(R.id.editTextTextNbNuits);

        Button buttonRecherche = this.findViewById(R.id.buttonRecherche);

        String[] dataNbPersonne = {"Simple", "Double", "Familiale"};
        ArrayAdapter<String> adapternbPersonne = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataNbPersonne);
        spinnerNbPersonne.setAdapter(adapternbPersonne);

        String[] dataCategorie = {"Motel", "Village"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataCategorie);
        spinnerCategorie.setAdapter(adapterCategorie);


    }
}