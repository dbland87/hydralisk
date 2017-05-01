package io.proxylabs.spawning_pool;

import io.proxylabs.spawning_pool.database.DbService;
import io.proxylabs.spawning_pool.http.HttpService;
import io.proxylabs.spawning_pool.unit_generator.UnitGenerator;
import org.quartz.SchedulerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    private static final String PROPERTY_SPAWN_INTERVAL = "spawn_interval_minutes";
    private static final String PATH_TO_PROPERTIES_FILE = "/etc/spawning_pool/config.properties";

    private static Properties properties = new Properties();
    private static DbService dbService;
    private static HttpService httpService;

    public static DbService getDbService() {
        return dbService;
    }
    public static HttpService getHttpService() {
        return httpService;
    }

    public static void main(String[] args )
    {
        //Pull config from properties file
        retrieveConfig();

        //Start unit generator
        initUnitGenerator();

        //Init db service
        dbService = DbService.getInstance(properties);

        //Init http service
        httpService = HttpService.getInstance(properties);
    }

    public static void retrieveConfig(){
        InputStream input = null;
        try {
            input = new FileInputStream(PATH_TO_PROPERTIES_FILE);
            properties.load(input);

        } catch (FileNotFoundException e){
            //TODO Log this, shutdown
        } catch (IOException e){
            //TODO Log this, shutdown
        }
    }

    public static void initUnitGenerator(){
        try {
            UnitGenerator unitGenerator = UnitGenerator.getInstance(Integer.parseInt(
                    properties.getProperty(PROPERTY_SPAWN_INTERVAL)));
            unitGenerator.start();
        } catch (SchedulerException e){
            //TODO Log this, shutdown
        }
    }
}
