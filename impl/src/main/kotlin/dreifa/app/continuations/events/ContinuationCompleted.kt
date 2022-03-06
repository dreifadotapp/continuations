package dreifa.app.continuations.events

import dreifa.app.continuations.ContinuationId
import dreifa.app.ses.Event
import dreifa.app.ses.EventFactory

object ContinuationCompletedFactory : EventFactory {

    fun create(continuationId: ContinuationId): Event {
        return Event(
            type = eventType(),
            aggregateId = continuationId.toString()
        )
    }

    override fun eventType(): String = "dreifa.app.continuations.events.ContinuationCompleted"

}

