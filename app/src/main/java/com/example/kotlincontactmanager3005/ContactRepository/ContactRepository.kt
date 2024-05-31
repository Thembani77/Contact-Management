package com.example.kotlincontactmanager3005.ContactRepository

import com.example.kotlincontactmanager3005.Room.Contact_DAO
import com.example.kotlincontactmanager3005.Room.Contacts

class ContactRepository (private val contactDao: Contact_DAO){

    val Contacts = contactDao.getAllContact()

    suspend fun insert(contacts: Contacts): Long{
        return contactDao.insertContact(contacts)
    }

    suspend fun delete(contacts: Contacts){
        return contactDao.deleteContact(contacts)
    }

    suspend fun update(contacts: Contacts){
        return contactDao.updateContact(contacts)
    }

    suspend fun deleteAll(){
        return contactDao.deleteAll()
    }

}