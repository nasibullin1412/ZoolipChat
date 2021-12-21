package com.homework.coursework.data.frameworks.network.utils

import com.homework.coursework.data.frameworks.network.dto.CreateDto
import com.homework.coursework.domain.entity.SubscribeData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StreamQuery @Inject constructor() {
    fun createStream(
        subscribeData: SubscribeData
    ): Map<String, String> {
        val createDto = CreateDto.createNarrowForMessage(subscribeData = subscribeData)
        return subscribeData.run{
            mapOf(
                "invite_only" to inviteOnly.toString(),
                "is_web_public" to isWebPublic.toString(),
                "history_public_to_subscribers" to historyPublicToSubscribers.toString(),
                "subscriptions" to createDto
            )
        }
    }
}
