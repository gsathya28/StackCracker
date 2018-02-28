package com.clairvoyance.stackcracker;

import java.io.Serializable;

/**
 * Created by Sathya on 1/30/2018.
 * User of the Stack
 */

class User implements Serializable {

    private String uid;
    private String name;


    public User(){

    }

    User(String uid, String name){
        this.uid = uid;
        this.name = name;
    }

    String getUid() {
        return uid;
    }
    String getName(){ return name; }

}
