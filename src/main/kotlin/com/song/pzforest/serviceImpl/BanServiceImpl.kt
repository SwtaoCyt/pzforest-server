package com.song.pzforest.serviceImpl;

import com.song.pzforest.entity.Ban;
import com.song.pzforest.mapper.BanMapper;
import com.song.pzforest.service.BanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songwentao
 * @since 2022-05-05
 */
@Service
open class BanServiceImpl : ServiceImpl<BanMapper, Ban>(), BanService {

    @Autowired
    lateinit var banMapper: BanMapper
    override fun selectBanPeopleByUserId(userId: Long): Ban {
       return banMapper.selectBanPeopleByUserId(userId)
    }

}
