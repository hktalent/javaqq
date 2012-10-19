package com.blee.service;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;

public interface JobPoolingService extends Executor {
    
	void execute(Runnable command);

	public void setRejectedJobHandler(RejectedExecutionHandler rejectedJobHandler);
	
	void start() throws Exception;

    void stop() throws Exception;
	
}
