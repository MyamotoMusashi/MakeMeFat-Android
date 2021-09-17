package com.knifed.makemefat.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "dateOfCreation")
    public String dateOfCreation;

    public Food(String name, int amount) {
        this.name = name;
        this.amount = amount;
        this.dateOfCreation = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
