package com.danieljacob.kafka.blog.nonblockingretries.config.blocking

import com.danieljacob.kafka.blog.nonblockingretries.logging.info
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile("blocking")
class ExponentialBackOffKafkaListener {
    private val log = LoggerFactory.getLogger(ExponentialBackOffKafkaListener::class.java)

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${topic}"]
    )
    fun onReceive(message: String) {
        log.info { "processing message: $message"}
        throw Exception()
    }
}