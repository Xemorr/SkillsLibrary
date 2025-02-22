package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class WeatherCondition extends Condition implements EntityCondition, LocationCondition {
    public WeatherCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        World world = boss.getWorld();
        return world.hasStorm();
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> location.getWorld().hasStorm());
    }
}
