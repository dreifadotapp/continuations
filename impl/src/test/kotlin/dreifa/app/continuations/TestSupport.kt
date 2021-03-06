package dreifa.app.continuations

import dreifa.app.chaos.Chaos
import dreifa.app.continuations.simple.SimpleContinuationRegistrar
import dreifa.app.registry.Registry
import dreifa.app.xunitpatterns.spy.Spy

class ThreeSteps(
    registry: Registry = SimpleContinuationRegistrar().register(),
    continuationId: ContinuationId = ContinuationId.random()
) : Continuable<Int, Int> {
    // setup continuations
    private val factory = registry.get(ContinuationFactory::class.java)
    private val continuation = factory.get(continuationId)

    // setup internal test support
    private val chaos = registry.getOrElse(Chaos::class.java, Chaos(emptyMap(), true))
    private val spy = registry.getOrElse(Spy::class.java, Spy())

    override fun exec(input: Int): Int {
        testDecoration("starting")
        // run a sequence of calculations
        val step1Result = continuation.execBlock("step1", Int::class) {
            testDecoration("step1")
            input * input
        }
        val step2Result = continuation.execBlock("step2", Int::class) {
            testDecoration("step2")
            step1Result + 1
        }
        return continuation.execBlock("step3", Int::class) {
            testDecoration("step3")
            step2Result + step2Result
        }
    }

    // only to control and spy on the test double - wouldn't expect this in real code
    private fun testDecoration(step: String) {
        spy.spy(step)
        chaos.chaos(step)
    }
}


class TestSupportRegistrations : ContinuableRegistrations {
    override fun iterator(): Iterator<ContinuableRegistration> {
        return listOf(ContinuableRegistration(ThreeSteps::class)).iterator()
    }
}

