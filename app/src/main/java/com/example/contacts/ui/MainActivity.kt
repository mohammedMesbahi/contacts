package com.example.contacts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.contacts.R
import com.example.contacts.dao.ContactDBHelper
import com.example.contacts.model.ContactModel

class MainActivity : AppCompatActivity() {
    private lateinit var doa : ContactDBHelper

    private lateinit var txt_name : TextView
    private lateinit var txt_phone : TextView

    private lateinit var btn_add : Button
    private lateinit var btn_delete : Button
    private lateinit var btn_show_all:Button

    private lateinit var contacts_layout : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_name = findViewById<TextView>(R.id.txt_name)
        txt_phone = findViewById<TextView>(R.id.txt_phone)
        btn_add = findViewById<Button>(R.id.btn_add)
        btn_delete = findViewById<Button>(R.id.btn_delete)
        btn_show_all = findViewById<Button>(R.id.btn_show_all)
        contacts_layout = findViewById<LinearLayout>(R.id.contacts_layout)

        doa = ContactDBHelper(this)

        btn_add.setOnClickListener {
            addContact()
        }
        btn_delete.setOnClickListener {
            deleteContact()
        }
        btn_show_all.setOnClickListener {
            showAllContacts()
        }
    }

    private fun showAllContacts() {
        val contacts = doa.getAllContacts()
        // Clear existing views in the layout
        contacts_layout.removeAllViews();
        // Create TextView for each contact and add to the layout
        contacts.forEach() { contact ->
            val textView = TextView(this)
            textView.text = contact.toString()
            contacts_layout.addView(textView)
        }
    }

    private fun deleteContact() {
        val name = txt_name.text.toString()
        val phone = txt_phone.text.toString()
        if(name.isNotEmpty() && phone.isNotEmpty()){
            val contact = ContactModel(name, phone, null)
            val status = doa.deleteContact(contact)
            if(status > -1){
                Toast.makeText(this, "Contact supprimé avec succès ...",
                    Toast.LENGTH_SHORT).show()
                clearEditTexts()
            } else{ Toast.makeText(this, "Suppression erronée!", Toast.LENGTH_SHORT).show() }
        } else{ Toast.makeText(this, "Champs vides!", Toast.LENGTH_SHORT).show() }
    }

    private fun addContact(){
        val name = txt_name.text.toString()
        val phone = txt_phone.text.toString()
        if(name.isNotEmpty() && phone.isNotEmpty()){
            val contact = ContactModel(name, phone, null)
            val status = doa.insertContact(contact)
            if(status > -1){
                Toast.makeText(this, "Contact ajouté avec succès ...",
                    Toast.LENGTH_SHORT).show()
                clearEditTexts()
            } else{ Toast.makeText(this, "Ajout erroné!", Toast.LENGTH_SHORT).show() }
        } else{ Toast.makeText(this, "Champs vides!", Toast.LENGTH_SHORT).show() }
    }
    fun clearEditTexts() {
        txt_name.text = ""
        txt_phone.text = ""
    }
}