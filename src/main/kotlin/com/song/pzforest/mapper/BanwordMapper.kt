package com.song.pzforest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.song.pzforest.entity.Banword
import org.apache.ibatis.annotations.Mapper

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
@Mapper
interface BanwordMapper : BaseMapper<Banword>{
    fun getAllBanWord(): Set<Banword?>?
}
