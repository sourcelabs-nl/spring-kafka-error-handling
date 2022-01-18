package com.danieljacob.kafka.blog.nonblockingretries.logging

import java.lang.Exception
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val Any.log get() = LoggerFactory.getLogger(this.javaClass)

inline fun Logger.info(block: () -> String) = log.info(block())

inline fun Logger.info(exception: Exception, block: (Exception) -> String) =
    log.info(block(exception), exception)


