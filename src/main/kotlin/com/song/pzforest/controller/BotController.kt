package com.song.pzforest.controller

import com.song.pzforest.service.BotService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.util.concurrent.TimeUnit

@Api(tags = ["bot"])
@RestController
class BotController {



    @Autowired
    lateinit var botService:BotService;
    lateinit var  bot:Bot;


    @ApiOperation("bot-login")
    @RequestMapping("login")
    fun getBot(qq:Long,password:String):String
    {
        bot = botService.getBot(qq,password)
        doLogin()
        return "ok"
    }


    @ApiOperation("bot-login123")
    fun doLogin()= runBlocking {
        bot.login()
        botService.afterLogin(bot);
        bot.messageDSL()

    }

    //发送菜单方法
    suspend fun Bot.sendMenu(qq: Long)
    {
        bot.getFriend(qq)?.sendMessage("你好，我是培正森林发布器，请输入'发送微博'开始！")
    }



    fun Bot.messageDSL() {


        //如果有人添加好友，直接同意
        this.eventChannel.subscribeAlways<NewFriendRequestEvent> {event->
            event.accept()
            bot.getGroup(869450527)?.sendMessage("机器人添加了新好友！QQ号是${event.fromId}")

            //发送菜单
            bot.sendMenu(event.fromId)
        }

        // 监听这个 bot 的来自所有群和好友的消息
        this.eventChannel.subscribeMessages {
            // 当接收到消息 == "你好" 时就回复 "你好!"
            "你好" reply "你好!"

            // 当消息 == "查看 subject" 时, 执行 lambda
            case("查看 subject") {
                when (subject) {
                    is Friend -> {
                        subject.sendMessage("消息主体为 Friend，你在发私聊消息")
                    }
                    is Group -> {
                        subject.sendMessage("消息主体为 Group，你在群里发消息")
                    }
                    is Member -> {
                        subject.sendMessage("消息主体为 Member，你在发临时消息")
                    }
                }

                // 在回复的时候, 一般使用 subject 来作为回复对象.
                // 因为当群消息时, subject 为这个群.
                // 当好友消息时, subject 为这个好友.
                // 所有在 MessagePacket(也就是此时的 this 指代的对象) 中实现的扩展方法, 如刚刚的 "reply", 都是以 subject 作为目标
            }


            // 当消息里面包含这个类型的消息时
            has<Image> {
                // this: MessagePacket
                // message: MessageChain
                // sender: QQ
                // it: String (MessageChain.toString)


                // message[Image].download() // 还未支持 download
                if (this is GroupMessageEvent) {
                    //如果是群消息
                    // group: Group
                    this.group.sendMessage("你在一个群里")
                    // 等同于 reply("你在一个群里")
                }

                subject.sendMessage("图片, ID= ${message[Image]}")//获取第一个 Image 类型的消息
                subject.sendMessage(message)
            }

            Regex("hello.*world") matchingReply {
                "Hello!"
            }

            "123" containsReply "你的消息里面包含 123"


            // 当收到 "我的qq" 就执行 lambda 并回复 lambda 的返回值 String
            "我的qq" reply { sender.id }

            "at all" reply AtAll // at 全体成员

            // 如果是这个 QQ 号发送的消息(可以是好友消息也可以是群消息)
            sentBy(123456789) {
            }


            // 当消息前缀为 "我是" 时
            startsWith("我是", removePrefix = true) {
                // it: 删除了消息前缀 "我是" 后的消息
                // 如一条消息为 "我是张三", 则此时的 it 为 "张三".

                subject.sendMessage("你是$it")
            }


            // listener 管理

            var repeaterListener: CompletableJob? = null
            contains("开启复读") {
                repeaterListener?.complete()
                eventChannel.subscribeGroupMessages {
                    repeaterListener = contains("复读") {
                        subject.sendMessage(message)
                    }
                }

            }

            contains("关闭复读") {
                if (repeaterListener?.complete() == null) {
                    subject.sendMessage("没有开启复读")
                } else {
                    subject.sendMessage("成功关闭复读")
                }
            }



            case("上传好友图片") {
                val filename = it.substringAfter("上传好友图片")
                File("C:\\Users\\Him18\\Desktop\\$filename").sendAsImageTo(subject)
            }

            case("上传群图片") {
                val filename = it.substringAfter("上传好友图片")
                File("C:\\Users\\Him18\\Desktop\\$filename").sendAsImageTo(subject)
            }
        }



        eventChannel.subscribeMessages {
            case("你好") {
                // this: MessagePacket
                // message: MessageChain
                // sender: QQ
                // it: String (来自 MessageChain.toString)
                // group: Group (如果是群消息)
                subject.sendMessage("你好")
            }
        }

        eventChannel.subscribeFriendMessages {
            contains("A") {
                // this: FriendMessage
                // message: MessageChain
                // sender: QQ
                // it: String (来自 MessageChain.toString)
                subject.sendMessage("B")
            }
        }

//        launch {
//            // channel 风格
//            for (message in this@messageDSL.incoming<FriendMessageEvent>()) {
//                println(message)
//            }
//            // 这个 for 循环不会结束.
//        }

        eventChannel.subscribeGroupMessages {
            // this: FriendMessage
            // message: MessageChain
            // sender: QQ
            // it: String (来自 MessageChain.toString)
            // group: Group

            case("recall") {
                subject.sendMessage("?").recallIn(3000) // 3 秒后自动撤回这条消息
            }

//            case("禁言") {
//                // 挂起当前协程, 等待下一条满足条件的消息.
//                // 发送 "禁言" 后需要再发送一条消息 at 一个人.
//                val value: At? = nextMessage { message.any(At) }[At]
//                value?.asMember()?.mute(10)
//            }

            startsWith("群名=") {
                if (!sender.isOperator()) {
                    sender.mute(5)
                    return@startsWith
                }
                group.name = it
            }
        }
    }
}