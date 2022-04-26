package com.song.pzforest.serviceImpl;

import com.song.pzforest.entity.Weibo;
import com.song.pzforest.mapper.WeiboMapper;
import com.song.pzforest.service.WeiboService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songwentao
 * @since 2022-04-25
 */
@Service
open class WeiboServiceImpl : ServiceImpl<WeiboMapper, Weibo>(), WeiboService {

}
