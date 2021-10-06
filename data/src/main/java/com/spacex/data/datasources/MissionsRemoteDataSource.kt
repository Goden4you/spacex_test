package com.spacex.data.datasources

import com.spacex.domain.entities.Mission
import com.spacex.domain.entities.Result

interface MissionsRemoteDataSource {
    suspend fun getAllMissions(): Result<List<Mission>>
}