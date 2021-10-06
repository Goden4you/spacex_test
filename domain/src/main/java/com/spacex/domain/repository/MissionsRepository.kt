package com.spacex.domain.repository

import com.spacex.domain.entities.Mission
import com.spacex.domain.entities.Result

interface MissionsRepository {
    suspend fun getAllMissions() : Result<List<Mission>>
}