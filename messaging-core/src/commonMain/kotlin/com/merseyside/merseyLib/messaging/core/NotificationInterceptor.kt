package com.merseyside.merseyLib.messaging.core

expect abstract class NotificationInterceptor {

    abstract val tag: String

    open fun isResponsibleFor(notification: Notification): Boolean

    fun intercept(notification: Notification)
}