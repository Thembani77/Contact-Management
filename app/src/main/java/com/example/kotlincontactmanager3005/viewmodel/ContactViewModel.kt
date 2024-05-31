package com.example.kotlincontactmanager3005.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincontactmanager3005.ContactRepository.ContactRepository
import com.example.kotlincontactmanager3005.Room.Contacts
import kotlinx.coroutines.launch

// ViewModel to manage UI-related data, separating UI logic from UI controllers (Activity and Fragment)
class ContactViewModel(private val repository: ContactRepository) : ViewModel(), Observable {

    // LiveData list of contacts from the repository
    val contacts = repository.Contacts

    // Flags and variables for update or delete operation
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contacts

    // Data Binding with LiveData for input fields and button texts
    @Bindable val inputName = MutableLiveData<String?>()
    @Bindable val inputEmail = MutableLiveData<String?>()
    @Bindable val saveOrUpdateButtonText = MutableLiveData<String>().apply { value = "Save" }
    @Bindable val clearAllOrDeleteButtonText = MutableLiveData<String>().apply { value = "Clear All" }

    // Insert a new contact
    private fun insert(contact: Contacts) = viewModelScope.launch {
        repository.insert(contact)
        resetFields()
    }

    // Delete a contact
    private fun delete(contact: Contacts) = viewModelScope.launch {
        repository.delete(contact)
        resetFields()
    }

    // Update a contact
    private fun update(contact: Contacts) = viewModelScope.launch {
        repository.update(contact)
        resetFields()
    }

    // Delete all contacts
    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        resetFields()
    }

    // Handle save or update action
    fun saveOrUpdate() {
        val name = inputName.value ?: return
        val email = inputEmail.value ?: return

        if (isUpdateOrDelete) {
            contactToUpdateOrDelete.apply {
                contact_name = name
                contact_email = email
            }
            update(contactToUpdateOrDelete)
        } else {
            insert(Contacts(0, name, email))
        }
    }

    // Handle clear all or delete action
    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(contactToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    // Initialize update and delete mode
    fun initUpdateAndDelete(contact: Contacts) {
        inputName.value = contact.contact_name
        inputEmail.value = contact.contact_email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    // Reset input fields and button texts
    private fun resetFields() {
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    // Observable interface methods (required for data binding)
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
