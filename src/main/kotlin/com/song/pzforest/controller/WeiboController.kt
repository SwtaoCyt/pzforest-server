package com.song.pzforest.controller



import com.song.pzforest.service.WeiboHttpServcie
import com.song.pzforest.service.WeiboService
import com.song.pzforest.serviceImpl.CacheService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
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



    @RequestMapping("send")
    @ApiOperation("测试")
    fun sendweibo(content:String)
    {
       weiboHttpServcie.getAt()
    }


}

