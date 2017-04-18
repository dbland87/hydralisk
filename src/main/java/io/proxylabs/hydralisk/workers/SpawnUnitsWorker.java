package io.proxylabs.hydralisk.workers;

import io.proxylabs.hydralisk.App;
import io.proxylabs.hydralisk.database.DbService;
import io.proxylabs.hydralisk.models.User;

import java.util.ArrayList;

/**
 * Created by dbland on 4/17/17.
 */
public class SpawnUnitsWorker {
    private DbService dbService;
    private ArrayList<User> allUsers = new ArrayList();

    public void start(){
        dbService = App.getDbService();
        allUsers = dbService.retrieveAllUsers();

        for (User user: allUsers){
            spawnEvent(user);
        }
    }

    private void spawnEvent(User user){
        //TODO Figure out which monster was spawned. Store in database. Send a push

    }
}
