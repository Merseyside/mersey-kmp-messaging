package com.merseyside.merseyLib.messaging.firebase

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

actual class FirebaseMessaging {

    private val mutTokenFlow =
        MutableSharedFlow<String>(onBufferOverflow = BufferOverflow.DROP_LATEST)
    actual val tokenFlow: Flow<String> = mutTokenFlow

}