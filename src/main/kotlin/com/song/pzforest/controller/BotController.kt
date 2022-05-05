package com.song.pzforest.controller

import cn.hutool.core.convert.Convert
import cn.hutool.core.io.FileUtil
import com.song.pzforest.service.BanService
import com.song.pzforest.service.BotService
import com.song.pzforest.service.WeiboService
import com.song.pzforest.serviceImpl.CacheService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.PlainText
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import kotlin.coroutines.CoroutineContext


@Api(tags = ["bot"])
@RestController
@Service
class BotController {

    @Autowired
    lateinit var botService: BotService;







    }
