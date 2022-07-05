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
@TableName("m_banword")
@ApiModel(value = "Banword对象", description = "")
class Banword : Serializable {

        @ApiModelProperty("限制词汇id")
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

        @ApiModelProperty("词汇内容")
    @TableField("content")
    var content: String? = null

        @ApiModelProperty("限制等级")
    @TableField("level")
    var level: Int? = null

        @ApiModelProperty("创建时间")
    @TableField("create_time")
    var createTime: Date? = null

        @ApiModelProperty("替换内容")
    @TableField("swap")
    var swap: String? = null

        @ApiModelProperty("发送字符长度低于length才会生效，默认全体生效")
    @TableField("length")
    var length: Int? = null

    override fun toString(): String {
        return "Banword{" +
        "id=" + id +
        ", content=" + content +
        ", level=" + level +
        ", createTime=" + createTime +
        ", swap=" + swap +
        ", length=" + length +
        "}"
    }
}
