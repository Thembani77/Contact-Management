package com.example.kotlincontactmanager3005.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Contacts::class], version = 1)
abstract class ContactDataBase: RoomDatabase() {

    abstract val contactDao : Contact_DAO

    companion object{
        @Volatile
        private var INSTANCE: ContactDataBase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): ContactDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDataBase::class.java,
                    "contact_db"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}