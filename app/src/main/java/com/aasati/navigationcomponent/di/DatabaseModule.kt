package com.aasati.navigationcomponent.di

import android.app.Application
import androidx.room.Room
import com.aasati.navigationcomponent.model.UserDao
import com.aasati.navigationcomponent.model.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "userdatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
}