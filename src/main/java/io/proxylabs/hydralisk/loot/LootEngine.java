package io.proxylabs.hydralisk.loot;

import io.proxylabs.hydralisk.models.LootTableItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dbland on 4/21/17.
 */
public class LootEngine {

    public LootTableItem getLootItem(ArrayList<LootTableItem> lootTableItems){
        ArrayList<LootTableItem> pool = new ArrayList();
        Random random = new Random();
        for (LootTableItem item : lootTableItems){
            for (int i = 0; i < item.getUnit_drop_weight(); i++ ){
                pool.add(item);
            }
        }
        int selection = random.nextInt(pool.size() + 1);
        return pool.get(selection);
    }
}
