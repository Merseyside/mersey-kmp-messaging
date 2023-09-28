package com.merseyside.merseyLib.messaging.firebase

import kotlinx.coroutines.flow.Flow

expect class FirebaseMessaging {

    val tokenFlow: Flow<String>
}