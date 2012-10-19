package com.blee.service;

import junit.framework.Assert;

import org.junit.Test;

import com.blee.util.ServiceUtil;

public class TestJobPoolingServiceImpl {

    @Test
    public void testExecutor() {
        JobPoolingService jobPoolingService = ServiceUtil.getPoolingService();
        //不太清楚这货要怎么测
        try {
            jobPoolingService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("run here");
                    Assert.assertEquals(true, true);
                }
            });
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
}
