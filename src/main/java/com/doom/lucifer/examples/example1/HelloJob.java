package com.doom.lucifer.examples.example1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class HelloJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Say Hello to the World and display the date/time
        _log.info("Hello World! - " + new Date());
    }
}
