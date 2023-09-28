package com.merseyside.merseyLib.messaging.firebase.data

import com.merseyside.merseyLib.messaging.firebase.FirebaseMessaging
import kotlinx.coroutines.flow.Flow

expect class FirebaseDataProvider(messaging: FirebaseMessaging) {

    val dataFlow: Flow<Map<String, Any>>
}