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
 * @since 2022-05-05
 */
@TableName("m_ban")
@ApiModel(value = "Ban对象", description = "")
class Ban : Serializable {

        @ApiModelProperty("用户ID（暂时为QQ号）")
    @TableId(value = "user_id", type = IdType.AUTO)
    var userId: Long? = null

    @TableField("user_type")
    var userType: String? = null

        @ApiModelProperty("封禁原因")
    @TableField("ban_cause")
    var banCause: String? = null

        @ApiModelProperty("创建时间")
    @TableField("create_time")
    var createTime: Date? = null

        @ApiModelProperty("封禁者")
    @TableField("create_by")
    var createBy: String? = null

    override fun toString(): String {
        return "Ban{" +
        "userId=" + userId +
        ", userType=" + userType +
        ", banCause=" + banCause +
        ", createTime=" + createTime +
        ", createBy=" + createBy +
        "}"
    }
}
