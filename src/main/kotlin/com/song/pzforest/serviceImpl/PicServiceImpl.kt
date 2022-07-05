package com.song.pzforest.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.song.pzforest.entity.Pic
import com.song.pzforest.mapper.PicMapper
import com.song.pzforest.service.PicService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
@Service
open class PicServiceImpl : ServiceImpl<PicMapper, Pic>(), PicService {

    @Value("\${webconfig.path}")
    lateinit var path:String
    private val logger = KotlinLogging.logger {}


    /**
     * 
     */
    override fun downPic(originalFileName: String): File {

        //获取文件类型，以最后一个`.`为标识
        val type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
        //获取文件名称（不包含格式）
        val name = originalFileName.substring(0, originalFileName.lastIndexOf("."))
        // 获取时间字符串
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
       //格式化文件名字
        val fileName = dateTimeFormatter + UUID.randomUUID().toString().replace("-".toRegex(), "") + "." + type
      //获取流
       val image= URL(originalFileName).openStream()
        //新建文件
        val file=File(path,fileName)
        //将字节流写入文件
        val data: ByteArray = image.readAllBytes()
        val outStream: FileOutputStream = FileOutputStream(file)
        outStream.write(data)
        //关闭输出流
        outStream.close();
        val pic=Pic()

        pic.path=path
        pic.name=fileName
        pic.type=type
        this.saveOrUpdate(pic)

        return file
    }

}
