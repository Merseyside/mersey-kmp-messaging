package com.merseyside.merseyLib.messaging.firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage


class MessageBroadcastReceiver private constructor(private val callback: MessagingCallback) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val token = MessagingService.tryToGetToken(intent)
        if (token != null) {
            callback.onToken(token)
        }

        val message = MessagingService.tryToGetMessage(intent)
        if (message != null) {
            callback.onMessage(message)
        }
    }

    fun unregisterReceiver(context: Context) {
        context.unregisterReceiver(this)
    }

    companion object {
        fun registerReceiver(
            context: Context,
            callback: MessagingCallback
        ): MessageBroadcastReceiver {
            val intentFilter = IntentFilter(MessagingService.ACTION_BROADCAST)
            return MessageBroadcastReceiver(callback).also { receiver ->
                ContextCompat.registerReceiver(
                    context,
                    receiver,
                    intentFilter,
                    ContextCompat.RECEIVER_NOT_EXPORTED
                )
            }
        }
    }

    interface MessagingCallback {
        fun onToken(token: String)

        fun onMessage(remoteMessage: RemoteMessage)
    }
}