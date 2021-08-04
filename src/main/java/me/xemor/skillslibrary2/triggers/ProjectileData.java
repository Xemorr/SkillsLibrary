package me.xemor.skillslibrary2.triggers;

import org.bukkit.configuration.ConfigurationSection;

public class ProjectileData extends TriggerData {

    private boolean onlyProjectiles;

    public ProjectileData(int trigger, ConfigurationSection configurationSection) {
        super(trigger, configurationSection);
        onlyProjectiles = configurationSection.getBoolean("onlyProjectiles");
    }

    public boolean onlyProjectiles() {
        return onlyProjectiles;
    }
}
