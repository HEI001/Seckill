package com.hei001.seckill.vo;

import com.hei001.seckill.pojo.User;
import com.hei001.seckill.vo.GoodsVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HEI001
 * @date 2022/3/1 18:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;

    private GoodsVo goodsVo;

    private int secKillStatus;

    private int remainSeconds;
}
