package com.example.spacextest.entities

data class SuccessedMission(
    val id: String,
    val name: String,
    val small: String?,
    val date_utc: String?,
    val date_unix: Int?,
    val success: Boolean?,
    val details: String?
)