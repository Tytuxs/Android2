package com.example.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketHandler implements Parcelable{
    private Socket Socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketHandler(Socket s, ObjectOutputStream oos, ObjectInputStream ois){
        this.Socket = s;
        this.oos = oos;
        this.ois = ois;
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

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
}
