package io.proxylabs.spawning_pool.unit_generator;

import io.proxylabs.spawning_pool.jobs.UnitGenerationJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by dbland on 4/16/17.
 */
public class UnitGenerator {

    private static UnitGenerator generator;
    private int interval;

    private UnitGenerator(int interval){
        this.interval = interval;
    }

    public static UnitGenerator getInstance(int interval){
        if (generator == null){
            generator = new UnitGenerator(interval);
        }
        return generator;
    }

    public void start() throws SchedulerException {
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(interval).repeatForever())
                .build();

        JobDetail job = JobBuilder.newJob(UnitGenerationJob.class)
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }
}
