package com.danieljacob.kafka.blog.nonblockingretries.config.exponential

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.ExponentialBackOff

@EnableKafka
@Configuration
@Profile("exponential")
class SimpleKafkaConfig {

    @Bean
    fun consumerFactory(properties: KafkaProperties): ConsumerFactory<String?, String?> =
        DefaultKafkaConsumerFactory(properties.buildConsumerProperties())

    @Bean
    fun kafkaListenerContainerFactory(properties: KafkaProperties): ConcurrentKafkaListenerContainerFactory<String?, String?> {
        val factory = ConcurrentKafkaListenerContainerFactory<String?, String?>()
        factory.consumerFactory = consumerFactory(properties)
        factory.setCommonErrorHandler(errorHandler(properties))
        return factory
    }

    @Bean
    fun kafkaErrorProducerFactory(kafkaProperties: KafkaProperties): DefaultKafkaProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties())
    }

    @Bean
    fun errorKafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, String> {
        return KafkaTemplate(kafkaErrorProducerFactory(kafkaProperties))
    }

    @Bean
    fun errorHandler(kafkaProperties: KafkaProperties): DefaultErrorHandler =
        run {
            val backOff = ExponentialBackOff(3000, 2.0)
            backOff.maxElapsedTime = 60000L
            DefaultErrorHandler(DeadLetterPublishingRecoverer(errorKafkaTemplate(kafkaProperties)), backOff)
        }
}