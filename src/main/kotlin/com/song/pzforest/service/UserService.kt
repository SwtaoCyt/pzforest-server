package com.song.pzforest.service;

import com.song.pzforest.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
interface UserService : IService<User>
{
    /**
     * 根据用户id获取用户
     */
    fun getUserByUserId(id:Int):User

    /**
     * 根据用户名获取用户
     */
    fun getUserByUserName(username:String):User


}

