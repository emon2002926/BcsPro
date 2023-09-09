package com.gdalamin.bcs_pro.dataClass;

import androidx.annotation.Keep;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gdalamin.bcs_pro.modelClass.model;


@Database(entities = {model.class}, version = 1)

@Keep
public abstract class AppDatabase extends RoomDatabase {
    public abstract ModelDao modelDao();
}