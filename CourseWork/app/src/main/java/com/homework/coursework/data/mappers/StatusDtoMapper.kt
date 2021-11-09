package com.homework.coursework.data.mappers

import com.homework.coursework.data.dto.StatusDto
import com.homework.coursework.data.dto.StatusResponse
import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.domain.entity.UserStatus
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class StatusDtoMapper : (StatusResponse) -> (UserStatus) {

    override fun invoke(status: StatusResponse): UserStatus {
        return status.data?.let {
            UserStatus(
                status = getStatus(status.data),
                timestamp = status.data.website.timestamp
            )
        } ?: throw IllegalArgumentException(status.msg)

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
