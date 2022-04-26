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
 * @since 2022-04-25
 */
@TableName("m_weibo")
@ApiModel(value = "Weibo对象", description = "")
class Weibo : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null

    @TableField("userid")
    var userid: String? = null

    @TableField("username")
    var username: String? = null

    @TableField("weiboid")
    var weiboid: String? = null

    @TableField("ip_id")
    var ipId: String? = null

    @TableField("ip_name")
    var ipName: String? = null

    @TableField("ip_address")
    var ipAddress: String? = null

    @TableField("content")
    var content: String? = null

    @TableField("date")
    var date: Date? = null

    @TableField("pic")
    var pic: String? = null

    override fun toString(): String {
        return "Weibo{" +
        "id=" + id +
        ", userid=" + userid +
        ", username=" + username +
        ", weiboid=" + weiboid +
        ", ipId=" + ipId +
        ", ipName=" + ipName +
        ", ipAddress=" + ipAddress +
        ", content=" + content +
        ", date=" + date +
        ", pic=" + pic +
        "}"
    }
}
