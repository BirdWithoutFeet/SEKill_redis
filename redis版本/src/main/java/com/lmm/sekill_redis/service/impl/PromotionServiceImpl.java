package com.lmm.sekill_redis.service.impl;

import com.lmm.sekill_redis.dao.PromotionDao;
import com.lmm.sekill_redis.entity.Promotion;
import com.lmm.sekill_redis.exception.PromotionException;
import com.lmm.sekill_redis.service.PromotionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Resource
    private PromotionDao promotionDao;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    public void promotionService(Long psId, String userId, Integer num) throws PromotionException {
        Promotion promotion = promotionDao.findById(psId);

//        约束
        if (promotion == null){
//            说明秒杀活动不存在
            throw new PromotionException("秒杀活动不存在");
        }
        if (promotion.getStatus() == 0){
            throw new PromotionException("秒杀活动还未开始");
        }else if (promotion.getStatus() == 2){
            throw new PromotionException("秒杀活动已经结束");
        }

        Boolean isExist = redisTemplate.opsForSet().isMember("seckill:users:" + promotion.getId(), userId);
        if (isExist){
            throw new PromotionException("不好意思，您已经抢购成功了，不能抢购第二次");
        }
        Integer goodId = (Integer) redisTemplate.opsForList().leftPop("seckill:count:" + promotion.getId());
        if (goodId != null){
//            不为空说明抢到了商品
            System.out.println("恭喜你:"+userId+"抢到商品，请支付");
            redisTemplate.opsForSet().add("seckill:users:"+promotion.getId(),userId);
        }else {
            promotion.setStatus(2);
            promotionDao.update(promotion);
            throw new PromotionException("抱歉，商品已经抢完了");
        }


    }
}
