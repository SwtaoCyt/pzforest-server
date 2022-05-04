package com.song.pzforest.mapper

import com.song.pzforest.entity.Weibo
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Mapper
interface WeiboMapper : BaseMapper<Weibo>{
   fun selectByWeiboId(id:String):Weibo


}

