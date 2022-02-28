package com.hei001.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.vo.LoginVo;
import com.hei001.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param loginVo
     * @return
     */
    RespBean doLogin(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);
}
