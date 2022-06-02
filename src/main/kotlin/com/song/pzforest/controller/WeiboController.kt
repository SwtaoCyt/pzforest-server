package com.song.pzforest.controller



import com.song.pzforest.service.BotService
import com.song.pzforest.service.WeiboHttpServcie
import com.song.pzforest.service.WeiboService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@RestController
@RequestMapping("/pzforest/weibo")
@Api(tags = ["微博"])
class WeiboController{



    @Autowired
    lateinit var weiboHttpServcie: WeiboHttpServcie

    @Autowired
    lateinit var weiboService: WeiboService

    @Autowired
    lateinit var botService: BotService
    @RequestMapping("send")
    @ApiOperation("测试")
    fun sendweibo(content:String)
    {
        weiboService.addWeibo("$content",2,"695901761")
    }

    //定时检测@我的微博，并实现发送
    @Scheduled(fixedRate = 1000 * 60 * 5)
    @RequestMapping("getAt")
    @ApiOperation("getAt")
    fun getAt() {

        weiboHttpServcie.getAt()
    }


    @RequestMapping("getToken")
    @ApiOperation("获取效果")
    fun getAccessToken(clientId: String,client_secret :String,code:String,redirect_uri:String){
        weiboHttpServcie.getAccessToken(clientId,client_secret,code,redirect_uri)
    }
}

