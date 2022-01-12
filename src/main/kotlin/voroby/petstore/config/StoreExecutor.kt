package voroby.petstore.config

import org.springframework.stereotype.Component
import voroby.petstore.service.Log
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.annotation.PreDestroy

class ThreadPoolFactory: ThreadFactory {

    private var count = 0

    override fun newThread(p: Runnable?): Thread =
        Thread(p, "StoreExecutor-${this.count++}")

}

@Component
class StoreExecutor(
    val executor: ThreadPoolExecutor =
        Executors.newCachedThreadPool(ThreadPoolFactory()) as ThreadPoolExecutor
) {

    companion object: Log()

    @PreDestroy
    private fun onDestroy() {
        log.info("Await termination for StoreExecutor: [activeThreads: ${executor.activeCount}]")
        try {
            executor.shutdown()
            executor.awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            log.warn("InterruptedException for StoreExecutor: [message: ${e.message}]")
            executor.shutdownNow()
        }
        log.info("StoreExecutor is terminated")
    }

}
