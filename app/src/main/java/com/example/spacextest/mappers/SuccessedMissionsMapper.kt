package com.example.spacextest.mappers

import com.example.spacextest.di.ServiceLocator.formatTo
import com.example.spacextest.di.ServiceLocator.toDate
import com.example.spacextest.entities.SuccessedMission
import com.spacex.domain.entities.Mission

class SuccessedMissionsMapper {
    fun fromAllToSuccessedMissions(
        missions: List<Mission>
    ): List<SuccessedMission> {
        val year2015Unix = 1420059600;
        val year2019Unix = 1577826000;
        val commonElements = missions.filter { it.success == true && it.date_unix in year2015Unix..year2019Unix }
        val successedMission = arrayListOf<SuccessedMission>()
        for (mission in missions) {
            if (mission in commonElements) {
                successedMission.add(
                    SuccessedMission(
                        mission.id,
                        mission.name,
                        mission.links?.patch?.small,
                        mission.date_utc?.toDate()?.formatTo("dd MMM yyyy"),
                        mission.date_unix,
                        mission.success,
                        mission.details
                    )
                )
            }
        }

        return successedMission.sortedByDescending { it.date_unix }
    }

    fun changeMissionsOrder(
        missions: List<SuccessedMission>,
        isReversed: Boolean
    ) : List<SuccessedMission> {
        return if (isReversed)
            missions.sortedByDescending { it.date_unix }
        else missions.sortedBy { it.date_unix }
    }
}