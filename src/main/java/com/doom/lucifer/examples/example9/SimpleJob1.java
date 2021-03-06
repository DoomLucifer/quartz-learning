package com.doom.lucifer.examples.example9;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SimpleJob1 implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob1.class);

    /**
     * Empty constructor for job initilization
     */
    public SimpleJob1() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("SimpleJob1 says: " + jobKey + " executing at " + new Date());
    }
}
