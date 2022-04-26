package com.song.pzforest

import net.mamoe.mirai.BotFactory.INSTANCE.newBot
import net.mamoe.mirai.utils.BotConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.oas.annotations.EnableOpenApi


@EnableOpenApi
@SpringBootApplication
class PzforestServerApplication

fun main(args: Array<String>) {
    runApplication<PzforestServerApplication>(*args)



}
