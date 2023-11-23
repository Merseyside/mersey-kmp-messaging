package com.merseyside.merseyLib.messaging.firebase.data

import com.merseyside.merseyLib.messaging.firebase.FirebaseMessaging
import kotlinx.coroutines.flow.Flow

actual class FirebaseDataProvider actual constructor(messaging: FirebaseMessaging) {

    actual val dataFlow: Flow<Map<String, Any>> = TODO()

    actual val tokenFlow: Flow<String>
        get() = TODO("Not yet implemented")

}