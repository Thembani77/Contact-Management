package com.example.kotlincontactmanager3005.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface Contact_DAO {

    @Insert
    suspend fun insertContact(contacts: Contacts): Long

    @Update
    suspend fun updateContact(contacts: Contacts)

    @Delete
    suspend fun deleteContact(contacts: Contacts)

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM contacts_table")
    fun getAllContact():LiveData<List<Contacts>>

}