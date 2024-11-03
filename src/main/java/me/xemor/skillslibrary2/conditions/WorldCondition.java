package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WorldCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    List<String> worlds;

    public WorldCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        worlds = configurationSection.getStringList("worlds");
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> worlds.contains(location.getWorld().getName()));
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return worlds.contains(entity.getWorld().getName());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> worlds.contains(target.getWorld().getName()));
    }
}
