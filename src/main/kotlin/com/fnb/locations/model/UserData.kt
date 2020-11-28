package com.fnb.locations.model

data class UserData(
        val id: String,
        val username: String,
        val contact: String,
        val description: String,
        val picture: String,
        val locations: List<String>
)