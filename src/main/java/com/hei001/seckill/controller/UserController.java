package com.hei001.seckill.controller;


import com.hei001.seckill.pojo.User;
import com.hei001.seckill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HEI001
 * @since 2022-02-28
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用户信息
     * 专门用来测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

}
