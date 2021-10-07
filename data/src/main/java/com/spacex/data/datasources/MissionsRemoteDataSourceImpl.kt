package com.spacex.data.datasources

import com.spacex.data.api.MissionsApi
import com.spacex.data.mappers.MissionApiResponseMapper
import com.spacex.domain.entities.Mission
import com.spacex.domain.entities.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MissionsRemoteDataSourceImpl(
    private val service: MissionsApi,
    private val mapper: MissionApiResponseMapper
) : MissionsRemoteDataSource {
    override suspend fun getAllMissions(): Result<List<Mission>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getAllMissions()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toMissionList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}