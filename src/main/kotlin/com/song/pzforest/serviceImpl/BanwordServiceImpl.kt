package com.song.pzforest.serviceImpl;

import com.song.pzforest.entity.Banword;
import com.song.pzforest.mapper.BanwordMapper;
import com.song.pzforest.service.BanwordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforest.mapper.BanMapper
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
open class BanwordServiceImpl : ServiceImpl<BanwordMapper, Banword>(), BanwordService {
   @Autowired
  lateinit var banwordMapper: BanwordMapper
    override fun getAllBanWord(): Set<Banword?>? {
        return banwordMapper.getAllBanWord()
    }

}
