package com.example.contacts.dao

import android.provider.BaseColumns

object DBContact {
    const val DATABASE_NAME = "contacts.db"
    const val DATABASE_VERSION = 1
    class ContactEntry : BaseColumns {
        companion object {
            val ID = "id"
            val TABLE_NAME = "contacts"
            val NAME = "name"
            val PHONE = "phone"
        }
    }
}