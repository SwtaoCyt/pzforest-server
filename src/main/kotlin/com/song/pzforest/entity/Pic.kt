package com.song.pzforest.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
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
@TableName("m_pic")
@ApiModel(value = "Pic对象", description = "")
class Pic : Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    var id: Long? = null

    @TableField("Path")
    var path: String? = null

    @TableField("Name")
    var name: String? = null

    @TableField("Type")
    var type: String? = null

    override fun toString(): String {
        return "Pic{" +
        "id=" + id +
        ", path=" + path +
        ", name=" + name +
        ", type=" + type +
        "}"
    }
}
