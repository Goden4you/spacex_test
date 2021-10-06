package com.spacex.domain.usecases

import com.spacex.domain.repository.MissionsRepository

class GetMissionsUseCase(private val missionsRepository: MissionsRepository) {

    suspend operator fun invoke() = missionsRepository.getAllMissions()
}