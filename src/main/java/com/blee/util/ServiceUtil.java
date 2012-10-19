package com.blee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blee.exception.ServiceException;
import com.blee.service.HttpService;
import com.blee.service.JobPoolingService;
import com.blee.service.impl.HttpServiceImpl;
import com.blee.service.impl.JobPoolingServiceImpl;

public class ServiceUtil {
    
    private static Logger log = LoggerFactory.getLogger(ServiceUtil.class);

    private static JobPoolingService jobPoolingService;
    
    /**
     * 共用一个算了,反正创建不会太多
     */
    private static Object serviceCreateLock = new Object();
    
    private static HttpService httpService;
    
    public static JobPoolingService getPoolingService() {
        if(jobPoolingService == null) {
            synchronized (serviceCreateLock) {
                if(jobPoolingService == null) {
                    jobPoolingService = new JobPoolingServiceImpl();
                    try {
                        jobPoolingService.start();
                    } catch (Exception e) {
                        log.error("can't start JobPoolingService!!!", e);
                        throw new ServiceException(e);
                    }
                }
            }
        }
        return jobPoolingService;
    }
    
    public static HttpService getHttpService() {
        if(httpService == null) {
            synchronized (serviceCreateLock) {
                if(httpService == null) {
                    httpService = new HttpServiceImpl();
                }
            }
        }
        return httpService;
    }
    
}
