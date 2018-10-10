package com.doom.lucifer;

import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class SimpleExampleTest {

    private static Logger logger = LoggerFactory.getLogger(SimpleExampleTest.class);

    @Test
    public void deleteJob() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = JobKey.jobKey("job1", "group1");
            scheduler.deleteJob(jobKey);
//            scheduler.triggerJob(jobKey);
//            scheduler.start();

//            Thread.sleep(60 * 1000);

//            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}