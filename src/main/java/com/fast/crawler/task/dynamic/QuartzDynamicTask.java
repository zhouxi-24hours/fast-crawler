package com.fast.crawler.task.dynamic;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @Package com.fast.crawler.task.dynamic
 * @Description: 任务调度 - 动态创建,删除定时任务
 * @Author zhouxi
 * @Date 2017/10/10 15:20
 */
public class QuartzDynamicTask {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private static final String DEFAULT_JOB_GROUP_NAME = "defaultJobGroupName";
    private static final String DEAFULT_TRRIGGER_NAME = "defaultTriggerName";
    private static final String DEFAULT_TRIGGER_GROUP_NAME = "defaultTriggerGroupName";
    private static final String DEFAULT_CRON = "0 0/30 * * * ?";  // 默认30分钟执行一次
    private static final long DEFAULT_FUTHER_TIME = 5 * 1000;     // 默认5分钟之后执行

    public static void addJob(String jobName, Class jobClass) {
        addJob(jobName,DEFAULT_JOB_GROUP_NAME,DEAFULT_TRRIGGER_NAME,DEFAULT_TRIGGER_GROUP_NAME,jobClass,DEFAULT_FUTHER_TIME,DEFAULT_CRON);
    }

    /**
     * @Description: 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass 任务
     * @param cron 时间设置
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, long futherTime, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build(); // 任务名,任务组,任务执行类
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();  // 触发器
            triggerBuilder.withIdentity(triggerName, triggerGroupName);  // 触发器名,触发器组
            long furtherTimes = System.currentTimeMillis() + futherTime;
            Date date = new Date(furtherTimes);
            triggerBuilder.startAt(date);  // 5秒之后执行
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron)); // 触发器时间设定
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();  // 创建Trigger对象
            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeJob(String jobName) {
        removeJob(jobName, DEFAULT_JOB_GROUP_NAME, DEAFULT_TRRIGGER_NAME, DEFAULT_TRIGGER_GROUP_NAME);
    }

    /**
     * @Description: 移除一个定时任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            sched.pauseTrigger(triggerKey);   // 停止触发器
            sched.unscheduleJob(triggerKey);  // 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));  // 删除任务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
