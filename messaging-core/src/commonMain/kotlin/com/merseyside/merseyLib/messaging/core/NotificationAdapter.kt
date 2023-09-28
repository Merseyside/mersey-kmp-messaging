package com.merseyside.merseyLib.messaging.core

expect abstract class NotificationAdapter {

    /**
     * @return true if notification successfully shown.
     */
    fun show(notification: Notification): Boolean

    fun setInterceptors(list: List<NotificationInterceptor>)
}