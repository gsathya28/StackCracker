package com.clairvoyance.stackcracker;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Sathya on 12/24/2017.
 * Serializer class was posted by Henrik Gustafsson
 * https://stackoverflow.com/questions/5837698/converting-any-object-to-a-byte-array-in-java
 */

class DataHandler {


    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }


    static User parseMainUserData(Context context){
        User mainUser = null;
        try {
            FileInputStream fIn = context.openFileInput("localUser");
            byte[] byteHolder = new byte[fIn.available()];
            int x = fIn.read(byteHolder);
            if (x != -1)
                mainUser = (User) deserialize(byteHolder);
        } catch (IOException|ClassNotFoundException i)
        {
            Log.d("Read Error: ", "Unable to read in data??!!");
        }

        return mainUser;
    }

    // Currently one usage in ActSellAddBook.java
    static void saveMainUserData(User mainUser, Context context){

        try {
            FileOutputStream fOut = context.openFileOutput("localUser", Context.MODE_PRIVATE);
            fOut.write(serialize(mainUser));
            fOut.close();
        } catch (IOException i) {
            Log.d("Save Error: ", i.getMessage());
        }
    }

}
