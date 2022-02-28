package com.hei001.seckill.exception;

import com.hei001.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HEI001
 * @date 2022/2/28 13:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
