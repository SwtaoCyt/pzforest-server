package com.song.pzforest.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@TableName("m_user")
@ApiModel(value = "User对象", description = "")
class User : Serializable {

        @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

        @ApiModelProperty("第三方应用的id")
    @TableField("user_id")
    var userId: Long? = null

        @ApiModelProperty("用户类型，0是微博，1是QQ")
    @TableField("user_type")
    var userType: Int? = null

        @ApiModelProperty("用户名")
    @TableField("user_name")
    var userName: String? = null

        @ApiModelProperty("用户头像")
    @TableField("avatar")
    var avatar: String? = null

        @ApiModelProperty("用户密码")
    @TableField("password")
    var password: String? = null

        @ApiModelProperty("封禁状态，0是正常，1是封禁")
    @TableField("status")
    var status: Int? = null

        @ApiModelProperty("创建日期")
    @TableField("created")
    var created: Date? = null

        @ApiModelProperty("最后登录时间")
    @TableField("last_login")
    var lastLogin: Date? = null

    override fun toString(): String {
        return "User{" +
        "id=" + id +
        ", userId=" + userId +
        ", userType=" + userType +
        ", userName=" + userName +
        ", avatar=" + avatar +
        ", password=" + password +
        ", status=" + status +
        ", created=" + created +
        ", lastLogin=" + lastLogin +
        "}"
    }
}
