package com.song.pzforest.service

import com.song.pzforest.entity.Weibo
import com.baomidou.mybatisplus.extension.service.IService

import java.io.File
import javax.print.attribute.IntegerSyntax

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

    fun selectByWeiboId(id:String):Weibo

    fun  checkBanWord(str: String):Map<String,Integer>

}
