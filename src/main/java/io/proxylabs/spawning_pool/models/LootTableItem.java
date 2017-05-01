package io.proxylabs.spawning_pool.models;

/**
 * Created by dbland on 4/21/17.
 */
public class LootTableItem {
    private int unit_id;
    private int unit_drop_weight;

    public LootTableItem(int unit_id, int unit_drop_weight){
        this.unit_id = unit_id;
        this.unit_drop_weight = unit_drop_weight;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getUnit_drop_weight() {
        return unit_drop_weight;
    }

    public void setUnit_drop_weight(int unit_drop_weight) {
        this.unit_drop_weight = unit_drop_weight;
    }
}
