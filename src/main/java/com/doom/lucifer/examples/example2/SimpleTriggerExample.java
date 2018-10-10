package com.doom.lucifer.examples.example2;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SimpleTriggerExample {

    public void run() throws Exception {
        Logger logger = LoggerFactory.getLogger(SimpleTriggerExample.class);

        logger.info("------- Initializing -------------------");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        logger.info("------- Initialization Complete --------");

        logger.info("------- Scheduling Jobs ----------------");

        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 will only fire once at date/time "ts"
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();

        // schedule it to run!
        Date ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(startTime).build();

        ft = scheduler.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job3 will run 11 times (run once and repeat 10 more times)
        // job3 will repeat every 10 seconds
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job3","group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger3","group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();

        ft = scheduler.scheduleJob(job,trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // the same job (job3) will be scheduled by a another trigger
        // this time will only repeat twice at a 70 second interval
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger3","group2").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2))
                .forJob(job).build();

        ft = scheduler.scheduleJob(trigger);
        logger.info(job.getKey() + " will [also] run at: " + ft + " and repeat: " + trigger.getRepeatCount()
                + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job4 will run 6 times (run once and repeat 5 more times)
        // job4 will repeat every 10 seconds
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job4","group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger4","group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5)).build();

        ft = scheduler.scheduleJob(job,trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job5 will run once, five minutes in the future
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job5","group1").build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger5","group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE)).build();

        ft = scheduler.scheduleJob(job,trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        logger.info("------- Starting Scheduler ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        scheduler.start();

        logger.info("------- Started Scheduler -----------------");

        // jobs can also be scheduled after start() has been called...
        // job7 will repeat 20 times, repeat every five minutes
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job7","group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger7","group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();

        ft = scheduler.scheduleJob(job,trigger);
        logger.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // jobs can be fired directly... (rather than waiting for a trigger)
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job8","group1").storeDurably().build();

        scheduler.addJob(job,true);
        logger.info("'Manually' triggering job8...");
        scheduler.triggerJob(JobKey.jobKey("job8","group1"));

        logger.info("------- Waiting 30 seconds... --------------");

        try {
            // wait 33 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }


        // jobs can be re-scheduled...
        // job 7 will run immediately and repeat 10 times for every second
        logger.info("------- Rescheduling... --------------------");
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger7","group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();

        ft = scheduler.rescheduleJob(trigger.getKey(),trigger);
        logger.info("job7 rescheduled to run at: " + ft);

        logger.info("------- Waiting five minutes... ------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(300L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        logger.info("------- Shutting Down ---------------------");

        scheduler.shutdown(true);

        logger.info("------- Shutdown Complete -----------------");

        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = scheduler.getMetaData();
        logger.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) {
        try {
            SimpleTriggerExample example = new SimpleTriggerExample();
            example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
