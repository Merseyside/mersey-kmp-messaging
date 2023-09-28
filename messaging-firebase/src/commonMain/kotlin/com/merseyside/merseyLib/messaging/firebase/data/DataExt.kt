package com.merseyside.merseyLib.messaging.firebase.data

import com.merseyside.merseyLib.messaging.firebase.FirebaseMessaging
import kotlinx.coroutines.flow.Flow

fun <T> FirebaseMessaging.toDataFlow(adapter: FirebaseDataAdapter<T>): Flow<T> {
    val dataHandler = FirebaseDataProvider(this)
    adapter.setFirebaseDataHandler(dataHandler)
    return adapter.dataFlow
}