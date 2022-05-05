package com.song.pzforest


import com.song.pzforest.service.BotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import springfox.documentation.oas.annotations.EnableOpenApi
import java.util.*
import javax.annotation.PostConstruct


@EnableOpenApi
@SpringBootApplication
@Configuration
@EnableScheduling
//@ForestScan(basePackages = ["com.song.pzforest.client"])
class PzforestServerApplication




fun main(args: Array<String>) {
    runApplication<PzforestServerApplication>(*args)

}

@Component
class CommandLineRunnerImpl : CommandLineRunner {

    @Autowired
    lateinit var botService: BotService
    @Throws(Exception::class)
    override fun run(vararg args: String) {
    botService.doLogin()
    }
}
