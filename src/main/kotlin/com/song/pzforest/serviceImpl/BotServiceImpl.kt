package com.song.pzforest.serviceImpl

import com.song.pzforest.service.BotService
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import java.io.File
import org.jetbrains.annotations.NotNull
import org.springframework.stereotype.Service
import kotlin.coroutines.CoroutineContext


@Service
open class BotServiceImpl:BotService{

    override fun getBot(qq: Long, password: String): Bot {
        return  BotFactory.newBot(qq, password, object : BotConfiguration() {
            init {
                fileBasedDeviceInfo()
                protocol = MiraiProtocol.ANDROID_PAD
            }
        })
    }

    override fun afterLogin(bot: Bot) {

    }




}