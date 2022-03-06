package dreifa.app.continuations.events

import dreifa.app.continuations.ContinuationId
import dreifa.app.ses.Event
import dreifa.app.ses.EventFactory

data class ContinuationStarted(
    val continuationIdAsString: String,
    val threadId: Long
) {
    constructor(continuationId: ContinuationId, threadId: Long) : this(continuationId.toString(), threadId)
}

object ContinuationStartedFactory : EventFactory {

    fun create(payload: ContinuationStarted): Event {

        return Event(
            type = eventType(),
            aggregateId = payload.continuationIdAsString,
            payload = payload
        )
    }

    override fun eventType(): String = "dreifa.app.continuations.events.ContinuationStarted"
}

