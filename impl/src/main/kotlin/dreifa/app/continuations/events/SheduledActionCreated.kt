package dreifa.app.continuations.events

import dreifa.app.continuations.ContinuationContext
import dreifa.app.continuations.simple.Scheduled
import dreifa.app.ses.Event
import dreifa.app.ses.EventFactory


data class ScheduledActionCreated(
    val key: String,
    val ctx: ContinuationContext,
    val clazzName: String,
    val scheduledTime: Long
)

object ScheduledActionCreatedFactory : EventFactory {

    fun create(action: Scheduled<Any>): Event {

        val payload = ScheduledActionCreated(
            key = action.key,
            ctx = action.ctx,
            clazzName = action.clazz.qualifiedName!!,
            scheduledTime = action.scheduledTime
        )
        return create(payload)
    }

    fun create(payload: ScheduledActionCreated): Event {

        return Event(
            type = eventType(),
            aggregateId = payload.key,
            payload = payload
        )
    }

    override fun eventType(): String = "dreifa.app.continuations.events.ScheduledActionCreated"

}