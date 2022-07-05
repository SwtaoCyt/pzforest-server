package com.song.pzforest.mapper;

import com.song.pzforest.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Mapper
interface UserMapper : BaseMapper<User>{
    fun getUserByUserId(id:Int):User

    fun getUserByUserName(username:String):User
}
