package com.hei001.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hei001.seckill.mapper.UserMapper;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.utils.MD5Util;
import com.hei001.seckill.utils.ValidatorUtil;
import com.hei001.seckill.vo.LoginVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        if(!ValidatorUtil.isMobile(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }

        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (null==user){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //判断手机号是否正确
        if (!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return null;
    }
}
