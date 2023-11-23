package com.example.contacts.model

class ContactModel(var name:String,var phone:String,var id:Int?) {
    override fun toString():String{
        return "Name: $name," +
                " Phone: $phone"
    }
}