package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;

public class WorldCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    List<String> worlds;

    public WorldCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        worlds = configurationSection.getStringList("worlds");
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        return worlds.contains(location.getWorld().getName());
    }

    @Override
    public boolean isTrue(Entity boss) {
        return worlds.contains(boss.getWorld().getName());
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return worlds.contains(target.getWorld().getName());
    }
}
