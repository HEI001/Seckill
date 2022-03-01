package com.hei001.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hei001.seckill.pojo.Goods;
import com.hei001.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo finGoodsVoByGoodsId(Long goodsId);
}
