package com.song.pzforest.serviceImpl

import cn.hutool.core.convert.Convert
import cn.hutool.core.date.DateField
import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import cn.hutool.core.io.FileUtil
import com.song.pzforest.service.BanService
import com.song.pzforest.service.BotService
import com.song.pzforest.service.WeiboService
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.whileSelect
import mu.KotlinLogging
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.message.*
import net.mamoe.mirai.message.action.Nudge.Companion.sendNudge
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import java.io.File
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import java.awt.SystemColor.text
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.coroutines.CoroutineContext

/**
 * 机器人实现逻辑
 */
@Service
open class BotServiceImpl:BotService{

    lateinit var bot: Bot;

    @Value("\${qq.id}")
    lateinit var qq:String


    @Value("\${qq.password}")
    lateinit var password:String

    var botService=this

    @Value("\${webconfig.path}")
    lateinit var path:String
    @Autowired
    lateinit var banService: BanService
    @Autowired
    lateinit var weiboService: WeiboService

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    lateinit var  cacheService: CacheService;
    private val logger = KotlinLogging.logger {}
    override fun getBot(qq: Long, password: String): Bot {
        return  BotFactory.newBot(qq, password, object : BotConfiguration() {
            init {
                fileBasedDeviceInfo()
                protocol = MiraiProtocol.ANDROID_PAD
            }
        })
    }

    override fun afterLogin(bot: Bot) {
        TODO("Not yet implemented")
    }

    override fun doLogin() {
        runBlocking {
            bot = botService.getBot(Convert.toLong(qq), password)
            bot.login()
            bot.messageDSL()

        }
    }





    //发送菜单方法
    suspend fun Bot.sendMenu(qq: Long) {
        bot.getFriend(qq)?.sendMessage("你好,我是培正森林bot！\n" +
                "输入下方命令开始（带上 / 号）\n" +
                "/发送微博\n" +
                "/意见反馈\n" +
                "/删除微博")
    }





    suspend fun Bot.getMessage(event:MessageEvent) {
        lateinit var text:String
        var image: InputStream
        var imageFile: File? =null
        var pic:Image?=null
        var map=HashMap<String,Objects>()


        event.subject.sendMessage("请在一条信息内发送图片和文字\n十分钟后超时")
        //循环接收数据
        event.selectMessages {
            Thread.sleep(1000);
            //超时时间10分钟
            timeout(60_0000) { event.subject.sendMessage("超时了，请下次再发");  }
            has<Image> {
                // this: MessagePacket
                // message: MessageChain
                // sender: QQ
                // it: String (MessageChain.toString)
                text=message.contentToString().replace("[图片]","")


                //下载图片
                val imageItem:Image? =message[Image]
                image= URL(it.queryUrl()).openStream()

                //获取文件类型，以最后一个`.`为标识
                val type = imageItem?.imageType.toString()
                //获取文件名称（不包含格式）
                val name = imageItem?.content.toString()
                // 获取时间字符串
                val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
                //格式化文件名字
                val fileName = dateTimeFormatter + UUID.randomUUID().toString().replace("-".toRegex(), "") + "." + type
                imageFile  = File(path,fileName)
                println(imageFile!!.absoluteFile)
                val data: ByteArray = image.readAllBytes()
                val outStream: FileOutputStream = FileOutputStream(imageFile)
                outStream.write(data)
                //关闭输出流
                outStream.close();
                println(image.toString())
                pic= message[Image]!!
                logger.info(message.toString())
            }
            has<PlainText> {
                text=message.contentToString().replace("[图片]","")
                subject.sendMessage("...")
            }


        }
        logger.info(text+ imageFile?.path.toString())

        //检查字数，如果
        if (text.length>129)
        {
            event.subject.sendMessage("字数太多了！")
            event.subject.sendMessage("当前字数${text.length}，还需要减${text.length-129}个字数。")
            bot.getMessage(event)
        }

        val check= weiboService.checkBanWord(text)
        if(check.containsValue(Integer(0))||check.containsValue(Integer(2))) {
            //检查是否有非法字符
            text = check.keys.iterator().next()
            Thread.sleep(1000);
            //如果他没有发送文字，那就补空格，懒狗处理方法
            if(text.isEmpty())
            {
                text="                    "
            }
            logger.info(cacheService.get(event.sender.id).toString() )

            if(cacheService.get(event.sender.id).equals("\"notsendyet\""))
            {
                event.subject.sendMessage("你要发送的内容是下面这些吗？")
                var testText=""
                if(text.trim().length>12)
                {
                    testText = "文字:${text.substring(0,4)}(在QQ中省略)${text.substring(text.length-4,text.length)}"
                }
                else
                {
                    testText=  "文字:"+text
                }
                event.subject.sendMessage("$testText")
                if(!ObjectUtils.isEmpty(pic)) {
                    event.subject.sendMessage("图片:1张")
//                    event.subject.sendMessage(pic!!)
                }
                else
                {
                    event.subject.sendMessage("图片:0张")
                }
                Thread.sleep(1000);

                event.subject.sendMessage(
                    "确认无误请输入'确认'\n" +
                            "取消或重新发送请输入'取消'"
                )
                event.whileSelectMessages{
                    default {
                        subject.sendMessage("如需发送图片与文字，请在一条信息内完成!!输入 '取消' 重新开始\n"+
                                "确认无误请输入'确认'\n")
                        true
                    }
                    "确认"{
                        if(!FileUtil.isEmpty(imageFile)) { weiboService.addWeibo(text,imageFile!!,2,event.sender.id.toString(),null) } else { weiboService.addWeibo(text,2,event.sender.id.toString()) }
                        subject.sendMessage("发送成功!")
                        bot.getGroup(869450527)?.sendMessage("有人发送微博了，内容:$text \nQQ号:${event.sender.id}")
                        false
                    }
                    "取消"{
                        //redis删除该记录
                        redisTemplate.opsForValue().getAndDelete(event.sender.id.toString())
                        subject.sendMessage("取消成功,输入 '/发送微博' 重新开始")
                        event.intercept()
                        false
                    }

                }
            }
        } else if(check.containsValue(Integer(1))){
            event.subject.sendMessage("你的内容存在违规内容，请重新发送，拦截次数过多会被拉黑，请注意言辞")
            bot.getMessage(event)
        }

    }



    suspend fun Bot.messageDSL() {
        //如果有人添加好友，直接同意
        this.eventChannel.subscribeAlways<NewFriendRequestEvent> { event ->
            val ban=   banService.selectBanPeopleByUserId(event.fromId)
            //如果黑名单没有
            if(ObjectUtils.isEmpty(ban)) {
                //接收好友请求
                event.accept()
                //通知管理群
                bot.getGroup(869450527)?.sendMessage("新好友!QQ号是${event.fromId}")
                //发送菜单
                Thread.sleep(5000);
                bot.sendMenu(event.fromId)
            }else{
                //在黑名单就直接拒绝得了
                event.reject()
            }
        }
        //开启监听
        this.eventChannel.subscribeAlways<MessageEvent> { event ->


            if(message.contentEquals("/意见反馈")) {
                subject.sendMessage("如果您对培正森林有什么好的建议，可以在此留言，我会为您转发给作者，结束请输入'/说完了'")
                whileSelectMessages {
                    default {
                        bot.getGroup(869450527)?.sendMessage(message)
                        subject.sendMessage("收到！")
                        true
                    }
                    "/说完了"{
                        false
                    }

                }
            }

            if(message.contentEquals("/删除微博")) {
                subject.sendMessage("删除微博只能手动操作,请在微博上私信博主,主动说明需要删除哪一条微博,博主看见了就会删除。")

            }

            if (message.contentEquals("/发送微博")||message.contentEquals("/发送微博\n")) {
                val ban =banService.selectBanPeopleByUserId(event.sender.id);
             if(ObjectUtils.isEmpty(ban))
             {
                 //获取缓存中的用户记录
                 val getUser:String? =redisTemplate.opsForValue().get(event.sender.id.toString())
                 //如果没有这个用户
                 if(ObjectUtils.isEmpty(getUser)) {

                     cacheService.add(sender.id.toString(),"notsendyet")
                     bot.getMessage(event)


                 } else{
                     var getExpire=redisTemplate.getExpire(event.sender.id.toString())
                     var nowDate=DateTime.now()
                     var nextDate = DateUtil.offset(nowDate,DateField.SECOND, Convert.toInt(getExpire))
                     subject.sendMessage("当前已经发送过了，或者正在发送当中，在$nextDate 后可以再次发送")

                 }
                 }
                else
             {
                 subject.sendMessage("你被禁止发送了，封禁原因:${ban.banCause},封禁时间:${ban.createTime}")
             }



            }

        }

//        // 监听这个 bot 的来自所有群和好友的消息
//        this.eventChannel.subscribeMessages {
//            // 当接收到消息 == "你好" 时就回复 "你好!"
//            "你好" reply "你好!"
//
//            // 当消息 == "查看 subject" 时, 执行 lambda
//            case("查看 subject") {
//                when (subject) {
//                    is Friend -> {
//                        subject.sendMessage("消息主体为 Friend，你在发私聊消息")
//                    }
//                    is Group -> {
//                        subject.sendMessage("消息主体为 Group，你在群里发消息")
//                    }
//                    is Member -> {
//                        subject.sendMessage("消息主体为 Member，你在发临时消息")
//                    }
//                }
//
//                // 在回复的时候, 一般使用 subject 来作为回复对象.
//                // 因为当群消息时, subject 为这个群.
//                // 当好友消息时, subject 为这个好友.
//                // 所有在 MessagePacket(也就是此时的 this 指代的对象) 中实现的扩展方法, 如刚刚的 "reply", 都是以 subject 作为目标
//            }
//
//
//            // 当消息里面包含这个类型的消息时
//            has<Image> {
//                // this: MessagePacket
//                // message: MessageChain
//                // sender: QQ
//                // it: String (MessageChain.toString)
//
//
//                // message[Image].download() // 还未支持 download
//                if (this is GroupMessageEvent) {
//                    //如果是群消息
//                    // group: Group
//                    this.group.sendMessage("你在一个群里")
//                    // 等同于 reply("你在一个群里")
//                }
//
//                subject.sendMessage("图片, ID= ${message[Image]}")//获取第一个 Image 类型的消息
//                subject.sendMessage(message)
//            }
//
//            Regex("hello.*world") matchingReply {
//                "Hello!"
//            }
//
//            "123" containsReply "你的消息里面包含 123"
//
//
//            // 当收到 "我的qq" 就执行 lambda 并回复 lambda 的返回值 String
//            "我的qq" reply { sender.id }
//
//            "at all" reply AtAll // at 全体成员
//
//            // 如果是这个 QQ 号发送的消息(可以是好友消息也可以是群消息)
//            sentBy(123456789) {
//            }
//
//
//            // 当消息前缀为 "我是" 时
//            startsWith("我是", removePrefix = true) {
//                // it: 删除了消息前缀 "我是" 后的消息
//                // 如一条消息为 "我是张三", 则此时的 it 为 "张三".
//
//                subject.sendMessage("你是$it")
//            }
//
//
//            // listener 管理
//
//            var repeaterListener: CompletableJob? = null
//            contains("开启复读") {
//                repeaterListener?.complete()
//                eventChannel.subscribeGroupMessages {
//                    repeaterListener = contains("复读") {
//                        subject.sendMessage(message)
//                    }
//                }
//
//            }
//
//            contains("关闭复读") {
//                if (repeaterListener?.complete() == null) {
//                    subject.sendMessage("没有开启复读")
//                } else {
//                    subject.sendMessage("成功关闭复读")
//                }
//            }
//
//
//
//            case("上传好友图片") {
//                val filename = it.substringAfter("上传好友图片")
//                File("C:\\Users\\Him18\\Desktop\\$filename").sendAsImageTo(subject)
//            }
//
//            case("上传群图片") {
//                val filename = it.substringAfter("上传好友图片")
//                File("C:\\Users\\Him18\\Desktop\\$filename").sendAsImageTo(subject)
//            }
//        }
//
//
//
//        eventChannel.subscribeMessages {
//            case("你好") {
//                // this: MessagePacket
//                // message: MessageChain
//                // sender: QQ
//                // it: String (来自 MessageChain.toString)
//                // group: Group (如果是群消息)
//                subject.sendMessage("你好")
//            }
//        }
//
//        eventChannel.subscribeFriendMessages {
//            contains("A") {
//                // this: FriendMessage
//                // message: MessageChain
//                // sender: QQ
//                // it: String (来自 MessageChain.toString)
//                subject.sendMessage("B")
//            }
//        }
//
////        launch {
////            // channel 风格
////            for (message in this@messageDSL.incoming<FriendMessageEvent>()) {
////                println(message)
////            }
////            // 这个 for 循环不会结束.
////        }
//
//        eventChannel.subscribeGroupMessages {
//            // this: FriendMessage
//            // message: MessageChain
//            // sender: QQ
//            // it: String (来自 MessageChain.toString)
//            // group: Group
//
//            case("recall") {
//                subject.sendMessage("?").recallIn(3000) // 3 秒后自动撤回这条消息
//            }
//
////            case("禁言") {
////                // 挂起当前协程, 等待下一条满足条件的消息.
////                // 发送 "禁言" 后需要再发送一条消息 at 一个人.
////                val value: At? = nextMessage { message.any(At) }[At]
////                value?.asMember()?.mute(10)
////            }
//
//            startsWith("群名=") {
//                if (!sender.isOperator()) {
//                    sender.mute(5)
//                    return@startsWith
//                }
//                group.name = it
//            }
//        }
    }

}

