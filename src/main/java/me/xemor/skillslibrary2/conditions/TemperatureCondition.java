package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import me.xemor.configurationdata.comparison.RangeData;

import java.util.concurrent.CompletableFuture;

public class TemperatureCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    private RangeData temperatureRange;

    public TemperatureCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        temperatureRange = new RangeData("temperature", configurationSection);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> temperatureRange.isInRange(location.getBlock().getTemperature()));
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return temperatureRange.isInRange(entity.getLocation().getBlock().getTemperature());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return isTrue(execution, entity, target.getLocation());
    }

}
