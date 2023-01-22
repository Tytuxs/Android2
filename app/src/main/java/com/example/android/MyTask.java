package com.example.android;

import android.os.AsyncTask;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import Classe.Utilisateur;


public class MyTask extends AsyncTask<String,Void, Socket> {
    public Socket CSocket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;

    public MyTask(SocketHandler socketHandler){
        this.CSocket = socketHandler.getSocket();
        this.oos = socketHandler.getObjectOutputStream();
        this.ois = socketHandler.getObjectInputStream();
    }

    @Override
    protected Socket doInBackground(String... strings) {

        String username = strings[0];
        String password = strings[1];

        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.set_nomUser(username);
            utilisateur.set_password(password);
            oos.writeObject(utilisateur);
            oos.flush();
        }
        catch (IOException e) {
            System.out.println("Erreur reseau ? [" + e + "]");
        }

        return CSocket;
    }


    public byte[] GenerateHash(String username, String password, int nbr) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
        Security.removeProvider("BC");
        // Confirm that positioning this provider at the end works for your needs!
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bdos = new DataOutputStream(baos);
        bdos.writeInt(nbr);
        md.update(username.getBytes(StandardCharsets.UTF_8));
        md.update(baos.toByteArray());
        md.update(password.getBytes());
        return md.digest();
    }

}
