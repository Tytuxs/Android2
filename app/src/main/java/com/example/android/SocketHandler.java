package com.example.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketHandler implements Parcelable{
    private static Socket Socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public SocketHandler(){

    }

    public SocketHandler(Parcel in) {
    }

    public static final Creator<SocketHandler> CREATOR = new Creator<SocketHandler>() {
        @Override
        public SocketHandler createFromParcel(Parcel in) {
            return new SocketHandler(in);
        }

        @Override
        public SocketHandler[] newArray(int size) {
            return new SocketHandler[size];
        }
    };

    public static synchronized Socket getSocket(){
        return Socket;
    }
    public static synchronized void setSocket(Socket socket){
        Socket = socket;
    }

    public static synchronized ObjectInputStream getObjectInputStream(){
        return ois;
    }

    public static synchronized ObjectOutputStream getObjectOutputStream(){
        return oos;
    }

    public static synchronized void setObjectInputStream(ObjectInputStream objectInputStream){
        ois = objectInputStream;
    }

    public static synchronized void setObjectOutputStream(ObjectOutputStream objectOutputStream){
        oos = objectOutputStream;
    }

    public static synchronized void closeSocket() {
        try {
            Socket.close();
            ois.close();
            oos.close();
            Socket = null;
            ois = null;
            oos = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
