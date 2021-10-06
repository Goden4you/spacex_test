package com.example.spacextest

import android.app.Application
import com.example.spacextest.di.ServiceLocator
import com.example.spacextest.mappers.SuccessedMissionsMapper
import com.spacex.data.repository.MissionsRepositoryImpl
import com.spacex.domain.usecases.GetMissionsUseCase
import timber.log.Timber

class SpaceXTestApplication : Application() {
    private val missionsRepository: MissionsRepositoryImpl
        get() = ServiceLocator.provideMissionsRepository(this)

    val getMissionsUseCase: GetMissionsUseCase
        get() = GetMissionsUseCase(missionsRepository)

    val successedMissionsMapper = SuccessedMissionsMapper()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}