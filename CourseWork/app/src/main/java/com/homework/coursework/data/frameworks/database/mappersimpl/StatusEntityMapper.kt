package com.homework.coursework.data.frameworks.database.mappersimpl

import com.homework.coursework.domain.entity.StatusEnum
import com.homework.coursework.domain.entity.UserStatus
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StatusEntityMapper @Inject constructor() : (Int, Long) -> (UserStatus) {
    override fun invoke(status: Int, timestamp: Long): UserStatus {
        return UserStatus(
            status = StatusEnum.fromIntToStatus(status),
            timestamp = timestamp
        )
    }
}
