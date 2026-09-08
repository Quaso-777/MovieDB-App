package com.example.moviesdb

import android.app.Application
import androidx.room.Room
import com.example.moviesdb.database.AppDatabase
import com.example.moviesdb.repository.MovieRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltAndroidApp
class MovieApplication : Application() {
}