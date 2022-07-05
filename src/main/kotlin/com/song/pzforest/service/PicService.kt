package com.song.pzforest.service;

import com.song.pzforest.entity.Pic;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.File

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
interface PicService : IService<Pic>{
    fun downPic(originalFileName:String): File

}

