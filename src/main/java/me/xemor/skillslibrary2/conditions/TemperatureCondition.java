package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import me.xemor.configurationdata.comparison.RangeData;

public class TemperatureCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    private RangeData temperatureRange;

    public TemperatureCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        temperatureRange = new RangeData("temperature", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        return temperatureRange.isInRange(location.getBlock().getTemperature());
    }

    @Override
    public boolean isTrue(Entity entity) {
        return isTrue(entity, entity.getLocation());
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(entity, target.getLocation());
    }

}
