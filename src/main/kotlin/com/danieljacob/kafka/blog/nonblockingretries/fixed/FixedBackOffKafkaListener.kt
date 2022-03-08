
package com.danieljacob.kafka.blog.nonblockingretries.fixed

import com.danieljacob.kafka.blog.log
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile("fixed")
class FixedBackOffKafkaListener {

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${topic}"]
    )
    fun onReceive(message: String) {
        log.info("processing message: $message")
        throw Exception()
    }
}