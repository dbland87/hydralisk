package io.proxylabs.hydralisk.jobs;

import io.proxylabs.hydralisk.workers.SpawnUnitsWorker;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by dbland on 4/16/17.
 */
public class UnitGenerationJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpawnUnitsWorker spawnUnitsWorker = new SpawnUnitsWorker();
        spawnUnitsWorker.start();
    }
}
