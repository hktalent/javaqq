package com.blee.service.impl;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.blee.service.JobPoolingService;

public class JobPoolingServiceImpl implements JobPoolingService, JobPoolingServiceImplMBean {
	private static final Logger LOG = Logger.getLogger(JobPoolingService.class
			.getName());
	private ThreadPoolExecutor executor;
	private BlockingQueue<Runnable> jobs;
	/**
	 * default poolSize is 10
	 */
	private int jobSize = 1024;

	private int threadSize = 10;
	/**
	 * default is 5 seconds
	 */
	private int keepAliveTime = 5000;

	private boolean await = true;
	private AtomicBoolean started = new AtomicBoolean(false);
	private AtomicBoolean shutingdown = new AtomicBoolean(false);

	private RejectedExecutionHandler rejectedJobHandler;
	private int coreThreadSize;

	private AtomicInteger rejected = new AtomicInteger(0);

	@Override
	public void start() throws Exception {
		jobs = new ArrayBlockingQueue<Runnable>(jobSize);
		executor = new ThreadPoolExecutor(coreThreadSize, threadSize,
				keepAliveTime, TimeUnit.MILLISECONDS, jobs,
				new RejectedExecutionHandlerWapper());
		started.set(true);
	}

	public int getLargestPoolSize() {
		return executor.getLargestPoolSize();
	}

	public long getTaskCount() {
		return executor.getTaskCount();
	}

	public int getPoolSize() {
		return executor.getPoolSize();
	}

	@Override
	public void stop() throws Exception {
		if (!shutingdown.get() && executor != null)
			executor.shutdown();
	}

	private class RejectedExecutionHandlerWapper implements
			RejectedExecutionHandler {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			rejected.incrementAndGet();
			if (!executor.isShutdown()) {
				if (rejectedJobHandler != null) {
					rejectedJobHandler.rejectedExecution(r, executor);
				} else {
					r.run();
				}
			} else {
				LOG.warning("executor is shuting down,ignore job " + r);
			}
		}
	}

	protected void checkStarted() {
	    if(!started.get()) {
	        throw new IllegalStateException("service is not started");
	    }
		if (shutingdown.get()) {
			throw new IllegalStateException("service is shuting down");
		}
	}

	public void shutdown(boolean waitForQueuingJobs) {
		if (shutingdown.compareAndSet(false, true)) {
			if (waitForQueuingJobs) {
				executor.shutdown();
			} else {
				if (await) {
					executor.shutdown();
				} else {
					List<Runnable> rs = executor.shutdownNow();
					LOG.warning("canceled jobs : " + rs);
				}
			}
			try {
				stop();
			} catch (Exception e) {
			}
		}
	}

	public void execute(Runnable job) {
		checkStarted();
		executor.execute(job);
	}

	public void setKeepAliveTime(int keepAliveTime) {
		if (keepAliveTime > 3000)
			this.keepAliveTime = keepAliveTime;
	}

	public void setThreadSize(int threadSize) {
		if (threadSize > 0)
			this.threadSize = threadSize;
	}

	public void setJobSize(int jobSize) {
		if (this.jobSize > 0)
			this.jobSize = jobSize;
	}

	public void setCoreThreadSize(int coreThreadSize) {
		if (coreThreadSize > 0 && coreThreadSize <= threadSize)
			this.coreThreadSize = coreThreadSize;
	}

	public void setRejectedJobHandler(
			RejectedExecutionHandler rejectedJobHandler) {
		this.rejectedJobHandler = rejectedJobHandler;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public int getActiveCount() {
		return executor.getActiveCount();
	}

	public long getCompletedTaskCount() {
		return executor.getCompletedTaskCount();
	}

	public int getCoreThreadSize() {
		return coreThreadSize;
	}

	public int getJobSize() {
		return jobSize;
	}

	public int getKeepAliveTime() {
		return (int) executor.getKeepAliveTime(TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return "JobPoolingService[jobSize=" + jobSize + ",threadSize="
				+ threadSize + ",coreThreadSize=" + coreThreadSize
				+ ",rejectedJobHandler=" + rejectedJobHandler + "]";
	}
	@Override
	public String getRuntimeInfo() {
		return "{poolSize="+getPoolSize()+"}";
	}

	public int getCurrentPoolSize() {
		return executor.getPoolSize();
	}

	public int getMaxThreadSize() {
		return executor.getMaximumPoolSize();
	}

	public int getRejectedCount() {
		return rejected.get();
	}

	public int getQueuingCount() {
		return jobs.size();
	}

}
