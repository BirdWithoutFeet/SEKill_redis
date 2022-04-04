package com.lmm.sekill_redis.service;

import com.lmm.sekill_redis.exception.PromotionException;

public interface PromotionService {

    /*处理秒杀
    * psId 秒杀id
    * userId 用户
    * num 用户抢购限制
    * */
    public void promotionService(Long psId,String userId,Integer num) throws PromotionException;
}
