package com.hei001.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hei001.seckill.exception.GlobalException;
import com.hei001.seckill.mapper.UserMapper;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.utils.CookieUtil;
import com.hei001.seckill.utils.MD5Util;
import com.hei001.seckill.utils.UUIDUtil;
import com.hei001.seckill.utils.ValidatorUtil;
import com.hei001.seckill.vo.LoginVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

/*        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        if(!ValidatorUtil.isMobile(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }*/

        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (null==user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断手机号是否正确
        if (!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成 cookie
        String ticket = UUIDUtil.uuid();
        //不放如session中，放入redis中
        //专门操作String类型
        redisTemplate.opsForValue().set("user"+ticket,user);
        //request.getSession().setAttribute(ticket,user);
        CookieUtil.addCookie(response,"userTicket",ticket);
        return RespBean.success();
    }

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    @Override
    public User getUserByCookie(HttpServletResponse response,String userTicket) {
        //返回之前判断是否为空
        if (StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user" + userTicket);
        CookieUtil.addCookie(response,"userTicket",userTicket);
        return user;
    }
}
