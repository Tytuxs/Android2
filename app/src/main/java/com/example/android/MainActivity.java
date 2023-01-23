package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import Classe.Utilisateur;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.buttonConnexion);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == this.findViewById(R.id.buttonConnexion)) {

            TextView username = this.findViewById(R.id.editTextUtilisateur);
            TextView mdp = this.findViewById(R.id.editTextMDP);


            try {
                InetAddress ip = InetAddress.getByName("192.168.1.43");
                Socket s = new Socket(ip, 5056);
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

                oos.writeObject("LOGIN");
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.set_nomUser(username.getText().toString());
                utilisateur.set_password(mdp.getText().toString());
                oos.writeObject(utilisateur);

                String reponse = (String) ois.readObject();
                System.out.println(reponse);

                SocketHandler socketHandler = new SocketHandler();
                socketHandler.setSocket(s);
                socketHandler.setOis(ois);
                socketHandler.setOos(oos);
                System.out.println(socketHandler.getSocket());
                System.out.println(socketHandler.getOis());
                System.out.println(socketHandler.getOos());
                Intent intent = new Intent(this, MenuROMP.class);
                //intent.putExtra("socket",socketHandler);
                finish();
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}