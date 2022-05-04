package com.song.pzforest


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import springfox.documentation.oas.annotations.EnableOpenApi


@EnableOpenApi
@SpringBootApplication
@Configuration
//@ForestScan(basePackages = ["com.song.pzforest.client"])
class PzforestServerApplication

fun main(args: Array<String>) {
    runApplication<PzforestServerApplication>(*args)
    
}
