package com.merseyside.merseyLib.messaging.firebase.data

import com.merseyside.merseyLib.messaging.firebase.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

actual class FirebaseDataProvider actual constructor(messaging: FirebaseMessaging) {

    actual val dataFlow: Flow<Map<String, Any>> =
        messaging.remoteMessageFlow.map { message -> message.data }

}