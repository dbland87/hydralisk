package io.proxylabs.hydralisk.workers;

import io.proxylabs.hydralisk.App;
import io.proxylabs.hydralisk.database.DbService;
import io.proxylabs.hydralisk.http.HttpService;
import io.proxylabs.hydralisk.models.Notification;
import io.proxylabs.hydralisk.models.Unit;
import io.proxylabs.hydralisk.models.User;

import java.util.ArrayList;

/**
 * Created by dbland on 4/17/17.
 */
public class SpawnUnitsWorker {

    private static final String SPAWN_NOTIFICATION_TITLE = "You caught a monster!";
    private static final String SPAWN_NOTIFICATION_BODY = " has been added to your collection.";

    private DbService dbService;
    private HttpService httpService;
    private ArrayList<User> allUsers = new ArrayList();

    public void start(){
        dbService = App.getDbService();
        httpService = App.getHttpService();
        allUsers = dbService.retrieveAllUsers();

        for (User user: allUsers){
            spawnEvent(user);
        }
    }

    private void spawnEvent(User user){
        //TODO Figure out which monster was spawned. Store in database. Send a push
        Unit unit = new Unit();

        Notification notification = new Notification(SPAWN_NOTIFICATION_TITLE, "A " + unit.getName() + SPAWN_NOTIFICATION_BODY);
        httpService.postNotification(notification);
    }
}
