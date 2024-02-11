package com.example.myapplication.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.entities.Movie

@Database(entities = [Movie::class], version = 6)
abstract class MovieDB : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

}
