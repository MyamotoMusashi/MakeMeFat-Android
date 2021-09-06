package com.knifed.makemefat.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Sleep {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "dateOfCreation")
    public String dateOfCreation;

    public Sleep(int duration) {
        this.duration = duration;
        this.dateOfCreation = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
