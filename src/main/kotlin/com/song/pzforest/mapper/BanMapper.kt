package com.song.pzforest.mapper;

import com.song.pzforest.entity.Ban;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
@Mapper
interface BanMapper : BaseMapper<Ban>{
    fun selectBanPeopleByUserId(userId:Long):Ban
}
