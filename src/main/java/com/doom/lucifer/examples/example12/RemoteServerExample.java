package com.doom.lucifer.examples.example12;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteServerExample {

    public void run() throws Exception{
        Logger log = LoggerFactory.getLogger(RemoteServerExample.class);

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- (Not Scheduling any Jobs - relying on a remote client to schedule jobs --");

        log.info("------- Starting Scheduler ----------------");

        // start the schedule
        sched.start();

        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting ten minutes... ------------");

        // wait five minutes to give our jobs a chance to run
        try {
            Thread.sleep(600L * 1000L);
        } catch (Exception e) {
            //
        }

        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        RemoteServerExample example = new RemoteServerExample();
        example.run();
    }
}
