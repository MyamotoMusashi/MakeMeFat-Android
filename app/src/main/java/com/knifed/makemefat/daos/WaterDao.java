package com.knifed.makemefat.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.knifed.makemefat.entities.Water;

import java.util.List;

@Dao
public interface WaterDao {
    @Query("SELECT * FROM water")
    List<Water> getAll();

    @Insert
    void insertWater(Water water);
}
