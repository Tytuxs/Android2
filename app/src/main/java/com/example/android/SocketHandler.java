package com.example.android;

import android.os.Parcel;
import android.os.Parcelable;
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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static synchronized ObjectOutputStream getOos() {
        return oos;
    }

    public static synchronized void setOos(ObjectOutputStream flux) {
        oos = flux;
    }

    public static synchronized ObjectInputStream getOis() {
        return ois;
    }

    public static synchronized void setOis(ObjectInputStream flux) {
        ois = flux;
    }

    public static synchronized Socket getSocket() {
        return Socket;
    }

    public static synchronized void setSocket(java.net.Socket socket) {
        Socket = socket;
    }
}
