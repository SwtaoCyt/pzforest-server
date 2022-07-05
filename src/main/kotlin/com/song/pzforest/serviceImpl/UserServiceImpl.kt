package com.song.pzforest.serviceImpl;

import com.song.pzforest.entity.User;
import com.song.pzforest.mapper.UserMapper;
import com.song.pzforest.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Service
open class UserServiceImpl : ServiceImpl<UserMapper, User>(), UserService {
    @Autowired
    lateinit var userMapper: UserMapper
    override fun getUserByUserId(id: Int): User {
        return userMapper.getUserByUserId(id)
    }

    override fun getUserByUserName(username: String): User {
        return userMapper.getUserByUserName(username)
    }

}
