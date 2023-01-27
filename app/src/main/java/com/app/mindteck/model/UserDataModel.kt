package com.app.mindteck.model


data class UserDataModel(

    val count: Int,
    val `data`: List<Data>,
    val message: Boolean,
    val statusCode: Int
)

data class Data(
    val address: String,
    val address2: String,
    val country: String,
    val default_image: Int,
    val email: String,
    val gender: Int,
    val home_number: String,
    val id: Int,
    val isEmergency: Int,
    val mobile: String,
    val name: String,
    val photo: String,
    val postcode: String,
    val state: String,
    val town: String
)

data class Country(val name:String,val flags:Flag)

data class Flag(val png:String)