package io.proxylabs.spawning_pool.workers;

import io.proxylabs.spawning_pool.App;
import io.proxylabs.spawning_pool.database.DbService;
import io.proxylabs.spawning_pool.http.HttpService;
import io.proxylabs.spawning_pool.loot.LootEngine;
import io.proxylabs.spawning_pool.models.*;

import java.util.ArrayList;

/**
 * Created by dbland on 4/17/17.
 */
public class SpawnUnitsWorker {

    private static final String DB_SPAWN_LOOT_TABLE = "LT_unit_spawn";
    private static final String DB_UNIT_TABLE = "units_unit";

    private DbService dbService;
    private HttpService httpService;
    private ArrayList<User> allUsers = new ArrayList();
    private ArrayList<LootTableItem> allLootTableItems = new ArrayList();

    public void start(){
        dbService = App.getDbService();
        httpService = App.getHttpService();
        allUsers = dbService.retrieveAllUsers();

        for (User user: allUsers){
            spawnEvent(user);
        }
    }

    private void spawnEvent(User user){
        LootEngine lootEngine = new LootEngine();

        allLootTableItems = dbService.retrieveLootTable(DB_SPAWN_LOOT_TABLE);
        LootTableItem lootItem = lootEngine.getLootItem(allLootTableItems);

        Unit unit = dbService.getUnitFromId(lootItem.getUnit_id());
        FcmToken token = dbService.getTokenFromUser(user);

        if (unit != null && token != null && token.getBody() != null){
            httpService.postNotification(token, unit);
            dbService.saveUnit(unit, user);
        }
    }
}
