package com.lmm.sekill_redis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Promotion {
    private Long id;
    private Integer goodId;
    private Integer count;
    private Date startTime;
    private Date endTime;
    private Integer status;
}
