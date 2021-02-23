package ru.byteparser

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SimpleByteParserApp

fun main(args: Array<String>) {
    SpringApplication.run(SimpleByteParserApp::class.java, *args)
}