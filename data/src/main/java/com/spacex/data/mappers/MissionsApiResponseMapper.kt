package com.spacex.data.mappers

import com.spacex.data.api.Item
import com.spacex.domain.entities.Links
import com.spacex.domain.entities.ImageLinks

import com.spacex.domain.entities.Mission

class MissionApiResponseMapper {
    fun toMissionList(response: List<Item>): List<Mission> {
        return response.map {
            Mission(
                it.id,
                it.name,
                Links(ImageLinks(it.links.patch.small)),
                it.date_utc,
                it.date_unix,
                it.success,
                it.details
            )
        }
    }
}