package com.example.tutorem;

import androidx.annotation.NonNull;

public class Rem {
    String name;
    int id;
    public Rem(String name,int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Rem{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
