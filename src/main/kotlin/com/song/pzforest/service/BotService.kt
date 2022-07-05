package com.song.pzforest.service

import net.mamoe.mirai.Bot

interface BotService {
    /**
     * 获取机器人
     */
    fun getBot(qq:Long,password:String):Bot

    /**
     * 登陆后执行
     */
    fun afterLogin(bot:Bot)

    /**
     * 尝试登录
     */
    fun  doLogin()
}