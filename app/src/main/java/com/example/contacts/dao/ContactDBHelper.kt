package com.example.contacts.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contacts.dao.DBContact.ContactEntry
import com.example.contacts.model.ContactModel

class ContactDBHelper(context: Context) :

    SQLiteOpenHelper(context, DBContact.DATABASE_NAME, null, DBContact.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create_query = ("CREATE TABLE ${ContactEntry.TABLE_NAME} ("+
                "${ContactEntry.ID} INT AUTO_INCREMENT PRIMARY KEY,"+
                "${ContactEntry.NAME} TEXT,"+
                "${ContactEntry.PHONE} TEXT)"
                )
        db?.execSQL(create_query)
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ContactEntry.TABLE_NAME}")
        this.onCreate(db)
    }
    fun insertContact(contact : ContactModel) : Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ContactEntry.NAME, contact.name)
        values.put(ContactEntry.PHONE, contact.phone)
        val success = db.insert(ContactEntry.TABLE_NAME, null, values)
        db.close()
        return success
    }
    fun getAllContacts() : ArrayList<ContactModel> {
        val contacts : ArrayList<ContactModel> = ArrayList()
        val query = "SELECT * FROM ${DBContact.ContactEntry.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor : Cursor?
        try{
            cursor = db.rawQuery(query, null)
        }catch(e:Exception){
            e.printStackTrace()
            return ArrayList()
        }
        var id :Int
        var name :String
        var phone :String
        var index:Int

        if(cursor.moveToFirst()){
            do {
                index = cursor.getColumnIndex(ContactEntry.ID)
                id = cursor.getInt(index)
                index = cursor.getColumnIndex(ContactEntry.NAME)
                name = cursor.getString(index)
                index = cursor.getColumnIndex(ContactEntry.PHONE)
                phone = cursor.getString(index)
                val contact = ContactModel(name, phone, id)
                contacts.add(contact)
            }while (cursor.moveToNext())
        }
        return contacts
    }
    fun deleteContactById(id : Int) : Int{
        val db = this.writableDatabase
        val success = db.delete(ContactEntry.TABLE_NAME, "id=$id"
            , null)

        db.close()
        return success
    }

    fun deleteContact(contact: ContactModel): Int {
        val db = this.writableDatabase
        val success = db.delete(ContactEntry.TABLE_NAME, "id=${contact.id}"
            , null)

        db.close()
        return success
    }
}