package com.knifed.makemefat.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.knifed.makemefat.entities.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM water")
    List<Place> getAll();

    @Insert
    void insertPlace(Place place);
}
