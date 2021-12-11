package com.homework.coursework.domain.entity

class SubscribeData private constructor(
    val name: String,
    val description: String,
    val inviteOnly: Boolean,
    val isWebPublic: Boolean,
    val historyPublicToSubscribers: Boolean
) {
    data class Builder(
        var name: String = "",
        var description: String = "",
        var inviteOnly: Boolean = false,
        var isWebPublic: Boolean = false,
        var historyPublicToSubscribers: Boolean = false
    ) {
        fun name(name: String) = apply { this.name = name }
        fun description(description: String) = apply { this.description = description }
        fun inviteOnly(inviteOnly: Boolean) = apply { this.inviteOnly = inviteOnly }
        fun isWebPublic(isWebPublic: Boolean) = apply { this.isWebPublic = isWebPublic }
        fun historyPublicToSubscribers(historyPublicToSubscribers: Boolean) = apply {
            this.historyPublicToSubscribers = historyPublicToSubscribers
        }

        fun build() = SubscribeData(
            name = name,
            description = description,
            inviteOnly = inviteOnly,
            isWebPublic = isWebPublic,
            historyPublicToSubscribers = historyPublicToSubscribers
        )
    }
}
