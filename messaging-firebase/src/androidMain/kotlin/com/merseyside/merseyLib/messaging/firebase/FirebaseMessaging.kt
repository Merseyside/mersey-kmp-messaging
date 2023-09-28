package com.merseyside.merseyLib.messaging.firebase

import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.merseyside.merseyLib.kotlin.logger.log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

actual class FirebaseMessaging(private val context: Context) {

    private val mutTokenFlow =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    actual val tokenFlow: Flow<String> = mutTokenFlow

    private val mutRemoteMessageFlow =
        MutableSharedFlow<RemoteMessage>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val remoteMessageFlow: Flow<RemoteMessage> = mutRemoteMessageFlow

    private val callback = object : MessageBroadcastReceiver.MessagingCallback {
        override fun onToken(token: String) {
            mutTokenFlow.tryEmit(token)
        }

        override fun onMessage(remoteMessage: RemoteMessage) {
            mutRemoteMessageFlow.tryEmit(remoteMessage)
        }
    }

    private val broadcastReceiver = MessageBroadcastReceiver.registerReceiver(context, callback)

    fun unregisterReceiver() {
        broadcastReceiver.unregisterReceiver(context)
    }
}