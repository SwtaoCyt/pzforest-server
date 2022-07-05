package com.song.pzforest.service;

import com.song.pzforest.entity.Ban;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
interface BanService : IService<Ban>{
    fun selectBanPeopleByUserId(userId:Long):Ban


}
