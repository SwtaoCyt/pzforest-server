package com.song.pzforest.service.abc

import lombok.Data
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.utils.BotConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * 机器人工具类
 */
@Data
@Service
class BotService {
    @Value("\${bot.qq}")
    private var qq:Long=437793081
    @Value("\${bot.password}")
    private  var password:String="ddd492843929"




    fun getBot():Bot
    {
          val bot:Bot=BotFactory.newBot(qq, password) {
            fileBasedDeviceInfo() // 使用 device.json 存储设备信息
            protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD // 切换协议
          }
        return bot;
    }



}


