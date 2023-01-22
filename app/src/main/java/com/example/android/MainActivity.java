package com.example.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.buttonConnexion);
        b.setOnClickListener(this);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if(v == this.findViewById(R.id.buttonConnexion)) {
            System.out.println("OKOKKOKOKOKOKO");
            TextView username = this.findViewById(R.id.editTextUtilisateur);
            TextView mdp = this.findViewById(R.id.editTextMDP);
            System.out.println(username.getText());
            System.out.println(mdp.getText());

            SocketHandler socketHandler = socketHandler("localhost");
            MyTask tache = new MyTask(socketHandler);

            Socket CSocket = null;
            try {
                CSocket = tache.execute(username.getText().toString(), mdp.getText().toString()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(CSocket);

            Intent intent = new Intent(this, RechercheChambre.class);
            finish();
            startActivity(intent);


        }
    }

    public SocketHandler socketHandler(String AddressIP) {
        Socket CSocket;
        SocketHandler socketHandler1 = new SocketHandler();

        try {
            CSocket = new Socket(AddressIP, 5056); //52000
            socketHandler1.setSocket(CSocket);
            String msg = "Connecter a la socket";
            System.out.println(msg + " " + CSocket);

            socketHandler1.setObjectOutputStream(new ObjectOutputStream(CSocket.getOutputStream()));

            socketHandler1.setObjectInputStream(new ObjectInputStream(CSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socketHandler1;
    }
}