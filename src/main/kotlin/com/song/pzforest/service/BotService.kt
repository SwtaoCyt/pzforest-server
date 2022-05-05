package com.song.pzforest.service

import net.mamoe.mirai.Bot

interface BotService {
    fun getBot(qq:Long,password:String):Bot

    fun afterLogin(bot:Bot)

    fun  doLogin()
}