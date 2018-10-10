package com.doom.lucifer.examples.example9;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class Job1Listener implements JobListener {

    private static Logger _log = LoggerFactory.getLogger(Job1Listener.class);

    public String getName() {
        return "job1_to_job2";
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        _log.info("Job1Listener says: Job Is about to be executed.");
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        _log.info("Job1Listener says: Job Execution was vetoed.");
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        _log.info("Job1Listener says: Job was executed.");

        JobDetail job2 = newJob(SimpleJob2.class).withIdentity("jbo2").build();

        Trigger trigger = newTrigger().withIdentity("job2Trigger").startNow().build();
        try{
            context.getScheduler().scheduleJob(job2,trigger);
        }catch (SchedulerException e){
            _log.warn("Unable to schedule job2!");
            e.printStackTrace();
        }
    }
}
