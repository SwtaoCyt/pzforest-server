package com.song.pzforest.service

import net.mamoe.mirai.Bot

interface BotService {
    /**
     * ��ȡ������
     */
    fun getBot(qq:Long,password:String):Bot

    /**
     * ��½��ִ��
     */
    fun afterLogin(bot:Bot)

    /**
     * ���Ե�¼
     */
    fun  doLogin()
}