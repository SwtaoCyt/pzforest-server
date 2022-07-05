package com.song.pzforest.service;

import com.baomidou.mybatisplus.extension.service.IService
import com.song.pzforest.entity.Banword

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
interface BanwordService : IService<Banword>{
    fun getAllBanWord(): Set<Banword?>?
}
