package dreifa.app.continuations.events

import dreifa.app.continuations.ContinuationId
import dreifa.app.ses.Event
import dreifa.app.ses.EventFactory

data class ContinuationFailed(val exception: String, val message: String)

object ContinuationFailedFactory : EventFactory {

    fun create(continuationId: ContinuationId, t: Throwable): Event {
        return Event(
            type = eventType(),
            aggregateId = continuationId.toString(),
            payload = ContinuationFailed(
                exception = t::class.qualifiedName ?: "unknown",
                message = t.message ?: "unknown"
            )
        )
    }

    override fun eventType(): String = "dreifa.app.continuations.events.ContinuationFailed"

}

