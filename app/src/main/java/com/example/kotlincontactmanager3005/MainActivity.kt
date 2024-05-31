package com.example.kotlincontactmanager3005

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincontactmanager3005.ContactRepository.ContactRepository
import com.example.kotlincontactmanager3005.Room.ContactDataBase
import com.example.kotlincontactmanager3005.Room.Contacts
import com.example.kotlincontactmanager3005.View.MyRecyclerViewAdapter
import com.example.kotlincontactmanager3005.databinding.ActivityMainBinding
import com.example.kotlincontactmanager3005.viewmodel.ContactViewModel
import com.example.kotlincontactmanager3005.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize Room database, repository, and ViewModel
        val dao = ContactDataBase.getInstance(applicationContext).contactDao
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)

        // Bind ViewModel to the layout
        binding.contactViewModel = contactViewModel
        binding.lifecycleOwner = this

        // Initialize RecyclerView
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayContactsList()
    }

    private fun displayContactsList() {
        contactViewModel.contacts.observe(this) {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it) { selectedItem: Contacts ->
                listItemClicked(selectedItem)
            }
        }
    }

    private fun listItemClicked(selectedItem: Contacts) {
        Toast.makeText(this, "Selected Name is: ${selectedItem.contact_name}", Toast.LENGTH_LONG).show()
        contactViewModel.initUpdateAndDelete(selectedItem)
    }
}
