package com.danieljacob.kafka.blog.nonblockingretries.nonblocking

import com.danieljacob.kafka.blog.log
import org.apache.kafka.common.errors.SerializationException
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy
import org.springframework.kafka.support.converter.ConversionException
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.messaging.converter.MessageConversionException
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
@Profile("non-blocking")
class NonBlockingKafkaListener {

    @RetryableTopic(
        attempts = "\${retry-attempts}",
        backoff = Backoff(delay = 200, multiplier = 3.0, maxDelay = 0),
        numPartitions = "1",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
        dltStrategy = DltStrategy.FAIL_ON_ERROR,
        exclude = [
            DeserializationException::class,
            SerializationException::class,
            MessageConversionException::class,
            ConversionException::class,
            MethodArgumentResolutionException::class,
            NoSuchMethodException::class,
            ClassCastException::class
        ]
    )

    @KafkaListener(
        id = "\${spring.kafka.consumer.group-id}",
        topics = ["\${topic}"]
    )
    fun onReceive(message: String) {
        log.info("processing message: $message")
        throw Exception()
    }
}