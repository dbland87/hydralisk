package io.proxylabs.hydralisk;

import io.proxylabs.hydralisk.unit_generator.UnitGenerator;
import org.quartz.SchedulerException;

public class App
{
    private static final int GENERATION_INTERVAL_MINUTES = 1;

    public static void main( String[] args )
    {
        //Start unit generator
        try {
            UnitGenerator unitGenerator = UnitGenerator.getInstance(GENERATION_INTERVAL_MINUTES);
            unitGenerator.start();
        } catch (SchedulerException e){
            //TODO Log this, shutdown
        }
    }
}
