package com.spacex.domain.entities

data class Mission(
    val id: String,
    val name: String,
    val links: Links?,
    val date_utc: String?,
    val date_unix: Int?,
    val success: Boolean?,
    val details: String?
)