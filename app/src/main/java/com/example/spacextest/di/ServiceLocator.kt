package com.example.spacextest.di

import android.content.Context
import com.spacex.data.api.BASE_URL
import com.spacex.data.api.NetworkModule
import com.spacex.data.datasources.MissionsRemoteDataSourceImpl
import com.spacex.data.mappers.MissionApiResponseMapper
import com.spacex.data.repository.MissionsRepositoryImpl
import java.text.SimpleDateFormat
import java.util.*

object ServiceLocator {
    private val networkModule by lazy {
        NetworkModule()
    }

    @Volatile
    var missionsRepository: MissionsRepositoryImpl? = null

    fun provideMissionsRepository(context: Context): MissionsRepositoryImpl {
        synchronized(this) {
            return missionsRepository ?: createMissionsRepository(context)
        }
    }

    private fun createMissionsRepository(context: Context): MissionsRepositoryImpl {
        val newRepo =
            MissionsRepositoryImpl(
                MissionsRemoteDataSourceImpl(
                    networkModule.createMissionsApi(BASE_URL),
                    MissionApiResponseMapper()
                )
            )
        missionsRepository = newRepo
        return newRepo
    }

    fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
}