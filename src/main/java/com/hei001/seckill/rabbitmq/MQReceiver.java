package com.hei001.seckill.rabbitmq;

import com.hei001.seckill.pojo.Order;
import com.hei001.seckill.pojo.SeckillMessage;
import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IGoodsService;
import com.hei001.seckill.service.IOrderInfoService;
import com.hei001.seckill.service.IOrderService;
import com.hei001.seckill.utils.JsonUtil;
import com.hei001.seckill.vo.GoodsVo;
import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息的消费者
 * @author HEI001
 * @date 2022/3/1 22:52
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    /**
     * 下单操作
     * @param msg
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive05(String msg){
        log.info("接受的消息"+msg);
         SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();

        GoodsVo goodsVo = goodsService.finGoodsVoByGoodsId(goodId);
        if (goodsVo.getStockCount()<1){
            return ;
        }
        //判断是否重复抢购
        //判断是否重复抢购
        Order order1 = (Order) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodId);
        if(order1!=null){
            return ;
        }

        orderService.seckill(user,goodsVo);
    }
   /* @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接受消息",msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接受消息",msg);
    }
    @RabbitListener(queues = "queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接受消息"+msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void receive03(Object msg){
        log.info("queue_direct01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_direct02")
    public void receive04(Object msg){
        log.info("queue_direct02接受消息",msg);
    }

    @RabbitListener(queues = "queue_topic01")
    public void receive05(Object msg){
        log.info("topict01接受消息"+msg);
    }
    @RabbitListener(queues = "queue_topic02")
    public void receive06(Object msg){
        log.info("topict02接受消息"+msg);

    }


    @RabbitListener(queues = "queue_headers01")
    public void receive05(Message msg){
        log.info("queue_headers01接受消息对象"+msg);
        log.info("queue_headers01接受消息"+new String(msg.getBody()));

    }
    @RabbitListener(queues = "queue_headers02")
    public void receive06(Message msg){
        log.info("queue_headers02接受消息对象"+msg);
        log.info("queue_headers02接受消息"+new String(msg.getBody()));


    }*/
}
