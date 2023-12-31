package com.merseyside.merseyLib.messaging.core

import com.merseyside.merseyLib.kotlin.utils.Id
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

actual class Notification private actual constructor(
    val tag: String,
    val notificationId: Id
) {

    internal lateinit var notificationDefinition: NotificationDefinition

    constructor(
        tag: String,
        notificationId: Id,
        definition: NotificationDefinition
    ) : this(tag, notificationId) {
        notificationDefinition = definition
    }

    fun updateDefinition(block: NotificationDefinition) {
        val oldDefinition = notificationDefinition
        notificationDefinition = {
            oldDefinition()
            block()
        }
    }

    /**
     * @return true if notification successfully showed.
     */
    @Throws(IllegalStateException::class)
    actual fun show(): Boolean {
        return notificationAdapter?.show(this@Notification)
            ?: throw IllegalStateException("Notification adapter doesn't set")
    }

    private var notificationAdapter: NotificationAdapter? = null

    internal actual fun setAdapter(notificationAdapter: NotificationAdapter) {
        this.notificationAdapter = notificationAdapter
    }
}

actual abstract class Converter<T> {

    abstract val tag: String

    actual fun createNotification(data: T, notificationId: Id): Notification {
        return Notification(
            tag = tag,
            notificationId = notificationId,
            convert(data)
        )
    }

    protected abstract fun convert(data: T): NotificationDefinition

    internal actual fun isResponsibleFor(clazz: KClass<*>): Boolean {
        return parameterizedType == clazz.java
    }

    private val parameterizedType = (this.javaClass
        .genericSuperclass as ParameterizedType)
        .actualTypeArguments[0] as Class<T>
}