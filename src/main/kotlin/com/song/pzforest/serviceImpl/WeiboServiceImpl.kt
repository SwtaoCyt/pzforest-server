package com.song.pzforest.serviceImpl;

import cn.hutool.core.date.DateTime
import com.alibaba.fastjson2.JSON
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.song.pzforest.entity.Weibo
import com.song.pzforest.mapper.PicMapper
import com.song.pzforest.mapper.WeiboMapper
import com.song.pzforest.service.WeiboHttpServcie
import com.song.pzforest.service.WeiboService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.util.*


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Service
open class WeiboServiceImpl : ServiceImpl<WeiboMapper, Weibo>(), WeiboService {


    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var weiboHttpService: WeiboHttpServcie


    @Autowired
    lateinit var cacheService: CacheService

    @Autowired
    lateinit var weiboMapper: WeiboMapper

    @Autowired
    lateinit var picMapper: PicMapper

    override fun test(content: String)
    {

    }


    /**
     * 获取weibo对象
     */
    fun getWeibo(content: String, picContent: File?, logintype: Int, userId: String, date: Date,weiboId: String?): Weibo? {
        var weibo= Weibo()
        weibo.content=content
        if (picContent != null) {
        weibo.pic=picContent.name
        }
        if(weiboId!=null){
            weibo.weiboId=weiboId
        }
        weibo.userType= logintype.toString()
        weibo.userId=userId
        weibo.date= DateTime.now()
        return weibo
    }





    /**
     * 添加微博方法，并且保存到数据库
     */
    override fun addWeibo(content: String, picContent: File?, logintype: Int, userId: String,weiboId: String?) {
        try {

            logger.info { "用户账号是$userId,账户类型是$logintype,发送内容是$content" }
            if (picContent != null) {
                weiboHttpService.send(content,picContent)
            }
            else{
                weiboHttpService.send(content)
            }
            var weibo=getWeibo(content,picContent,logintype,userId,DateTime.now(),weiboId)
            this.save(weibo)
            cacheService.add(userId, JSON.toJSONString(weibo))
            logger.info { "发送成功！内容是$content,图片是${picContent!!.name}" }
        }catch (e:Exception){
            logger.debug { "报错了:$e" }
        }
    }




    override fun addWeibo(content: String, logintype: Int, userId: String) {
        this.addWeibo(content,null,logintype,userId,null)
    }

    override fun addWeibo(content: String, logintype: Int, userId: String, weiboId: String) {

        this.addWeibo(content,null,logintype,userId,weiboId)
    }

    override fun addWeibo(weibo: String) {
        TODO("Not yet implemented")
    }

    override fun selectByWeiboId(id: String): Weibo {
     return weiboMapper.selectByWeiboId(id)
    }




}
