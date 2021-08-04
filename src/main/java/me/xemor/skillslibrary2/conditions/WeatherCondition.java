package me.xemor.skillslibrary2.conditions;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class WeatherCondition extends Condition implements EntityCondition {
    public WeatherCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity boss) {
        World world = boss.getWorld();
        return world.hasStorm();
    }
}
