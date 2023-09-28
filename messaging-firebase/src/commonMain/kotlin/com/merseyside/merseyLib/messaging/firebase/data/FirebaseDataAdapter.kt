package com.merseyside.merseyLib.messaging.firebase.data

import kotlinx.coroutines.flow.*

abstract class FirebaseDataAdapter<Data>() {

    private lateinit var firebaseDataHandler: FirebaseDataProvider

    lateinit var dataFlow: Flow<Data>

    constructor(handler: FirebaseDataProvider) : this() {
        setFirebaseDataHandler(handler)
    }

    internal fun setFirebaseDataHandler(handler: FirebaseDataProvider) {
        this.firebaseDataHandler = handler
        dataFlow = firebaseDataHandler.dataFlow
            .filter { data -> isResponsibleFor(data) }
            .map(::map)
    }

    abstract fun isResponsibleFor(data: Map<String, Any>): Boolean

    abstract fun map(data: Map<String, Any>): Data
}