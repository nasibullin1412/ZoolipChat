package com.homework.coursework.data.frameworks.network.mappersimpl

import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.domain.entity.UserStatus
import dagger.Reusable
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@Reusable
@ExperimentalSerializationApi
class StatusDtoMapper @Inject constructor() : (StatusDto) -> (UserStatus) {

    override fun invoke(status: StatusDto): UserStatus {
        return UserStatus(
            status = getStatus(status),
            timestamp = status.website.timestamp
        )
    }

    private fun getStatus(status: StatusDto): StatusEnum {
        if (status.website.isActive() || status.aggregated.isActive()) {
            return StatusEnum.ACTIVE
        }
        if (status.website.isIdle() || status.aggregated.isIdle()) {
            return StatusEnum.IDLE
        }
        return StatusEnum.OFFLINE
    }
}
