package com.song.pzforest.service

import java.io.File

/*
远程调用的两个接口
 */
interface WeiboHttpServcie {

    fun send(status:String,picContent: File)

    fun send(status: String)

    fun getAt()


}