package com.merseyside.merseyLib.messaging.firebase

import android.content.Intent
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.kotlin.utils.safeLet

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val intent = Intent(ACTION_BROADCAST).apply {
            putExtra(TOKEN_KEY, token)
        }
        sendBroadcast(intent)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(ACTION_BROADCAST).apply {
            putExtra(MESSAGE_KEY, message)
        }
        sendBroadcast(intent)
    }

    companion object {
        fun tryToGetToken(intent: Intent?): String? {
            safeLet(intent, intent?.extras) { i, extras ->
                if (i.action == ACTION_BROADCAST) {
                    if (extras.containsKey(TOKEN_KEY)) return extras.getString(TOKEN_KEY)
                }
            }

            return null
        }

        fun tryToGetMessage(intent: Intent?): RemoteMessage? {
            return safeLet(intent, intent?.extras) { i, extras ->
                if (i.action == ACTION_BROADCAST) {
                    if (extras.containsKey(MESSAGE_KEY)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            extras.getParcelable(MESSAGE_KEY, RemoteMessage::class.java)
                        } else {
                            extras.getParcelable(MESSAGE_KEY)
                        }
                    } else null
                } else null
            }
        }

        private const val TOKEN_KEY = "token"
        private const val MESSAGE_KEY = "message"

        internal const val ACTION_BROADCAST = "mersey_messaging_action"
    }
}