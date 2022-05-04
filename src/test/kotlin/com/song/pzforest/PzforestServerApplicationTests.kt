package com.song.pzforest

import com.song.pzforest.serviceImpl.CacheService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PzforestServerApplicationTests {


    @Autowired
    lateinit var cacheService: CacheService
    @Test
    fun contextLoads() {

            cacheService.add(123,"abc")
            cacheService.get(123)

    }

}
