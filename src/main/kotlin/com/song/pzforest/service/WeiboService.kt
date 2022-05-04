package com.song.pzforest.service

import com.song.pzforest.entity.Weibo
import com.baomidou.mybatisplus.extension.service.IService

import java.io.File

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
interface WeiboService : IService<Weibo>{

    fun addWeibo(content: String, picContent: File?, logintype: Int, userId: String,weiboId:String?)

    fun addWeibo(content:String,logintype:Int,userId:String)

    fun addWeibo(content:String, logintype: Int, userId: String, weiboId:String)

    fun addWeibo(weibo: String)

    fun selectByWeiboId(id:String):Weibo

    fun test(content: String)
}
