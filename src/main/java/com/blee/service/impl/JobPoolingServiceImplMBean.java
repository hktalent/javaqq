package com.blee.service.impl;

public interface JobPoolingServiceImplMBean {
	/**
	 * 可分配的线程数目上限
	 * 
	 * @return
	 */
	public int getMaxThreadSize();

	/**
	 * 同时在池中的线程数目的峰值
	 * 
	 * @return
	 */
	public int getLargestPoolSize();

	/**
	 * 保持在池中的线程数目，even idle
	 * 
	 * @return
	 */
	public int getCoreThreadSize();

	/**
	 * 当前的线程数目
	 * 
	 * @return
	 */
	int getCurrentPoolSize();

	/**
	 * 任务队列的size，超过这个size就会block住，然后
	 * 
	 * @return
	 */
	public int getJobSize();

	/**
	 * 超过CoreThreadSize数目的线程空闲时保持活动的时间
	 * 
	 * @return
	 */
	public int getKeepAliveTime();

	/**
	 * 正在执行任务的线程数目
	 * 
	 * @return
	 */
	public int getActiveCount();

	/**
	 * 完成的任务数目
	 * 
	 * @return
	 */
	public long getCompletedTaskCount();

	/**
	 * 提交的总任务数目
	 * 
	 * @return
	 */
	long getTaskCount();

	/**
	 * 被线程池拒绝的任务数
	 * 
	 * @return
	 */
	int getRejectedCount();

	/**
	 * 正在排队的任务数
	 * 
	 * @return
	 */
	int getQueuingCount();
	
	String getRuntimeInfo();
	
	// int getCurrentFreeMemroy();
	// int getAll
}
