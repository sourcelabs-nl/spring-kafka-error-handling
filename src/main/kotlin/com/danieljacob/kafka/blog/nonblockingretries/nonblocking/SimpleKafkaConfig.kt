package com.danieljacob.kafka.blog.nonblockingretries.nonblocking

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

@EnableKafka
@Configuration
@Profile("non-blocking")
class SimpleKafkaConfig {

    @Bean
    fun consumerFactory(properties: KafkaProperties): ConsumerFactory<String?, String?> =
        DefaultKafkaConsumerFactory(properties.buildConsumerProperties())


    @Bean
    fun kafkaListenerContainerFactory(properties: KafkaProperties): ConcurrentKafkaListenerContainerFactory<String?, String?> =
        ConcurrentKafkaListenerContainerFactory<String?, String?>()
            .apply { consumerFactory = consumerFactory(properties) }

    @Bean
    fun kafkaErrorProducerFactory(kafkaProperties: KafkaProperties): DefaultKafkaProducerFactory<String, String> =
        DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties())

    @Bean
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, String> =
        KafkaTemplate(kafkaErrorProducerFactory(kafkaProperties))
    }