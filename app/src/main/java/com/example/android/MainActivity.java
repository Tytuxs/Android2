package com.example.android;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.buttonConnexion);
        b.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == this.findViewById(R.id.buttonConnexion)) {
            System.out.println("OKOKKOKOKOKOKO");
            TextView username = this.findViewById(R.id.editTextUtilisateur);
            TextView mdp = this.findViewById(R.id.editTextMDP);
            System.out.println(username.getText());
            System.out.println(mdp.getText());
            Intent intent = new Intent(this, RechercheChambre.class);
            finish();
            startActivity(intent);
        }
    }
}