package com.song.pzforest.controller;


import cn.dev33.satoken.stp.StpUtil
import com.song.pzforest.service.UserService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Api(tags = ["用户控制器"])
@RestController
@RequestMapping("/pzforest/user")
class UserController{

    private val logger = KotlinLogging.logger {}


    @Autowired
    lateinit var userService: UserService
    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    @ApiOperation("登录")
    fun doLogin(username: String, password: String): String? {
        val user = userService.getUserByUserName(username)
        if(ObjectUtils.isEmpty(user)) {
            return "没有这个用户！"
        }
        if(user.status==-1) {
            return "你被封号了！"
        }
        if(user.password==password) {
            StpUtil.login(user.id)
            return "登录成功"
        }
        return "登录失败"
    }

}

