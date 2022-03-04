package com.danieljacob.kafka.blog

import org.slf4j.LoggerFactory

val Any.log get() = LoggerFactory.getLogger(this::class.java)