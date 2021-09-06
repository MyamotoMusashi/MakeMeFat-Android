package com.knifed.makemefat.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Place {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "dateOfCreation")
    public String dateOfCreation;

    public Place(String name) {
        this.name = name;
        this.dateOfCreation = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
