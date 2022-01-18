
package com.danieljacob.kafka.blog.nonblockingretries.config.fixed

import com.danieljacob.kafka.blog.nonblockingretries.logging.info
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile("fixed")
class FixedBackOffKafkaListener {
    private val log = LoggerFactory.getLogger(FixedBackOffKafkaListener::class.java)

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${topic}"]
    )
    fun onReceive(message: String) {
        log.info { "processing message: $message"}
        throw Exception()
    }
}