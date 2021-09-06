package com.knifed.makemefat.database;

import android.annotation.SuppressLint;

import com.knifed.makemefat.daos.PlaceDao;
import com.knifed.makemefat.daos.SleepDao;
import com.knifed.makemefat.daos.WaterDao;
import com.knifed.makemefat.entities.Place;
import com.knifed.makemefat.entities.Sleep;
import com.knifed.makemefat.entities.Water;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@SuppressLint("RestrictedApi")
@Database(entities = {Water.class, Sleep.class, Place.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WaterDao waterDao();
    public abstract SleepDao sleepDao();
    public abstract PlaceDao placeDao();
}
