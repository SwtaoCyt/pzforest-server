package com.song.pzforest.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.Date
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data

/**
 * <p>
 * 
 * </p>
 *
 * @author songwentao
 * @since 2022-05-01
 */
@Data
@TableName("m_weibo")
@ApiModel(value = "Weibo对象", description = "")
class Weibo : Serializable {

        @ApiModelProperty("贴文id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

        @ApiModelProperty("发布端来源,0是微博登录，1是微博艾特，2是QQ机器人")
    @TableField("user_type")
    var userType: String? = null

        @ApiModelProperty("用户id")
    @TableField("user_id")
    var userId: String? = null

        @ApiModelProperty("用户名")
    @TableField("user_name")
    var userName: String? = null

        @ApiModelProperty("用户的mac(未实现)")
    @TableField("user_mac")
    var userMac: String? = null

        @ApiModelProperty("微博的帖子id，转发的时候会有记录")
    @TableField("weibo_id")
    var weiboId: String? = null

        @ApiModelProperty("ip(未实现)")
    @TableField("ip_id")
    var ipId: String? = null

        @ApiModelProperty("ip名字(未实现)")
    @TableField("ip_name")
    var ipName: String? = null

        @ApiModelProperty("ip地段(未实现)")
    @TableField("ip_address")
    var ipAddress: String? = null

        @ApiModelProperty("贴文内容")
    @TableField("content")
    var content: String? = null

        @ApiModelProperty("发送日期")
    @TableField("date")
    var date: Date? = null

        @ApiModelProperty("图片")
    @TableField("pic")
    var pic: String? = null

    override fun toString(): String {
        return "Weibo{" +
        "id=" + id +
        ", userType=" + userType +
        ", userId=" + userId +
        ", userName=" + userName +
        ", userMac=" + userMac +
        ", weiboId=" + weiboId +
        ", ipId=" + ipId +
        ", ipName=" + ipName +
        ", ipAddress=" + ipAddress +
        ", content=" + content +
        ", date=" + date +
        ", pic=" + pic +
        "}"
    }
}
