package com.lmm.sekill_redis.dao;

import com.lmm.sekill_redis.entity.Promotion;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PromotionDao {
    public Promotion findById(Long id);

    public List<Promotion> findUnStartPromotion();

    public void update(Promotion promotion);
}
