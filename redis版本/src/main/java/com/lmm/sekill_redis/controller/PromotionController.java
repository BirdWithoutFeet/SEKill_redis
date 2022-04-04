package com.lmm.sekill_redis.controller;

import com.lmm.sekill_redis.exception.PromotionException;
import com.lmm.sekill_redis.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @RequestMapping("/sk")
    @ResponseBody
    public Map processPromotion(Long psId , String userId ){
        HashMap<String, Object> result = new HashMap<>();
        try {
            promotionService.promotionService(psId,userId,1);
            result.put("code",0);
            result.put("message","success");
        } catch (PromotionException e) {
            result.put("code","500");
            result.put("message",e.getMessage());
        }
        return result;
    }
}
