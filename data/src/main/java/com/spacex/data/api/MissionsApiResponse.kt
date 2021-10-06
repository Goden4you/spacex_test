package com.spacex.data.api

import com.squareup.moshi.Json

data class Item(
    @field:Json(name = "id")
    val id : String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "links")
    val links: ApiLinks,
    @field:Json(name = "date_utc")
    val date_utc: String?,
    @field:Json(name = "date_unix")
    val date_unix: Int?,
    @field:Json(name = "success")
    val success: Boolean?,
    @field:Json(name = "details")
    val details: String?
)

data class ApiLinks(val patch: ImageLinks)

data class ImageLinks(val small: String?, val large: String?)