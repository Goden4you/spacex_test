package com.spacex.data.api

import retrofit2.Response
import retrofit2.http.GET

interface MissionsApi {
    @GET("launches")
    suspend fun getAllMissions(): Response<List<Item>>
}