package me.xemor.skillslibrary2.triggers;

import me.xemor.configurationdata.JsonPropertyWithDefault;

public class ProjectileData extends TriggerData {

    @JsonPropertyWithDefault
    private boolean onlyProjectiles = false;

    public boolean onlyProjectiles() {
        return onlyProjectiles;
    }
}
