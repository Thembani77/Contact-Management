package com.example.kotlincontactmanager3005.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincontactmanager3005.R
import com.example.kotlincontactmanager3005.Room.Contacts
import com.example.kotlincontactmanager3005.databinding.CardItemBinding

class MyRecyclerViewAdapter(
    private val contactsList: List<Contacts>,
    private val clickListener: (Contacts) -> Unit
) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position], clickListener)
    }

    override fun getItemCount(): Int = contactsList.size

    class MyViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contacts, clickListener: (Contacts) -> Unit) {
            binding.apply {
                nameTextView.text = contact.contact_name
                emailTextView.text = contact.contact_email
                listItemLayout.setOnClickListener { clickListener(contact) }
            }
        }
    }
}

