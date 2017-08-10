package com.htdf.pi.collect;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class SimpleTriggerQuartz {
	//public static void main(String[] args) throws SchedulerException {
	public void run() throws SchedulerException{
		JobKey jobKey = new JobKey("jobName", "group1");
		JobDetail job = JobBuilder.newJob(HelloJob.class)
		.withIdentity(jobKey).build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity("triggerName", "group1")
		.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))//SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
		.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		//scheduler.getListenerManager().addJobListener(new HelloJobListener(),KeyMatcher.keyEquals(jobKey));
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
	//}
}
