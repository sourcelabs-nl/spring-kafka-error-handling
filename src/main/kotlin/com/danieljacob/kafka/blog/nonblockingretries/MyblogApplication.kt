package com.danieljacob.kafka.blog.nonblockingretries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyblogApplication

fun main(args: Array<String>) {
    runApplication<MyblogApplication>(*args)
}
