package com.hei001.seckill.vo;

import com.hei001.seckill.pojo.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HEI001
 * @date 2022/3/1 19:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private OrderInfo order;

    private GoodsVo goodsVo;
}
