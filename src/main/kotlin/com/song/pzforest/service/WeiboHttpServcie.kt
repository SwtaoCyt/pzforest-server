package com.song.pzforest.service

import java.io.File

/*
Զ�̵��õ������ӿ�
 */
interface WeiboHttpServcie {

    fun send(status:String,picContent: File)

    fun send(status: String)

    fun getAt()


}