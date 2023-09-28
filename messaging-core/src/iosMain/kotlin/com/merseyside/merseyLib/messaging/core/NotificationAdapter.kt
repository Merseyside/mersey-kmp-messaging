package com.merseyside.merseyLib.messaging.core

actual abstract class NotificationAdapter {
    /**
     * @return true if notification successfully shown.
     */
    actual fun show(notification: Notification): Boolean {
        TODO("Not yet implemented")
    }

    actual fun setInterceptors(list: List<NotificationInterceptor>) {
    }
}