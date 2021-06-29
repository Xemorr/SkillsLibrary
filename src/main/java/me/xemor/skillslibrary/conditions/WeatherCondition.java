package me.xemor.skillslibrary.conditions;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class WeatherCondition extends Condition implements EntityCondition {
    public WeatherCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        World world = boss.getWorld();
        return world.hasStorm();
    }
}
