package com.spacex.data.repository

import com.spacex.data.datasources.MissionsRemoteDataSource
import com.spacex.domain.entities.Mission
import com.spacex.domain.entities.Result
import com.spacex.domain.repository.MissionsRepository

class MissionsRepositoryImpl(
    private val remoteDataSource: MissionsRemoteDataSource,
) : MissionsRepository {
    override suspend fun getAllMissions(): Result<List<Mission>> {
        return remoteDataSource.getAllMissions()
    }
}