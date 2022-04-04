package com.lmm.sekill_redis.exception;

/*自定义秒杀类*/
public class PromotionException extends Exception{
//    定义一个有参构造
    public PromotionException(String msg){
        super(msg);
    }
}
