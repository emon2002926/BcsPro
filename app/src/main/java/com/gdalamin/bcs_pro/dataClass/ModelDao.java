package com.gdalamin.bcs_pro.dataClass;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gdalamin.bcs_pro.modelClass.model;

import java.util.List;

@Dao
public interface ModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModels(List<model> models);

    @Query("SELECT * FROM models")
    List<model> getAllModels();
    @Query("SELECT * FROM models WHERE batch LIKE :batchName")
    List<model> getModelsByBatch(String batchName);

    @Update
    void updateModel(model model);
    // You can add more queries here as needed
}