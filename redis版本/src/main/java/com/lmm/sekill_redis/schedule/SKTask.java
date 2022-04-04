package com.lmm.sekill_redis.schedule;

import com.lmm.sekill_redis.dao.PromotionDao;
import com.lmm.sekill_redis.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/*
* 活动秒杀启动类*/
@Component
public class SKTask {

    @Resource
    private PromotionDao promotionDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /*从0秒开始执行，然后每隔5秒执行一次*/
    @Scheduled(cron = "0/5 * * * * ?")
    public void startSkill(){
        List<Promotion> ps = promotionDao.findUnStartPromotion();

        if (ps == null){
            System.out.println("目前还没有秒杀的活动");
        }else {
            for (Promotion p : ps) {
//                删除以前可能存在的重复的任务
                redisTemplate.delete("seckill:count:"+p.getId());

                for (int i = 0; i < p.getCount(); i++) {
//                    key seckill:count:秒杀id value goodId   初始化队列,右进   seckill:count:20220322
//                    存在一个问题：插入后，发现取不出来（序列化问题，默认是JDK的序列化）
                    redisTemplate.opsForList().rightPush("seckill:count:" + p.getId(), p.getGoodId());
                }
//                List<Object> list = redisTemplate.opsForList().range("seckill:count:" + p.getId(), 0, -1);
//                Object leftPop =  redisTemplate.opsForList().leftPop("seckill:count:" + p.getId());
                System.out.println(p.getId()+"活动已经启动！");
                p.setStatus(1);
                promotionDao.update(p);
            }
        }

    }
}
