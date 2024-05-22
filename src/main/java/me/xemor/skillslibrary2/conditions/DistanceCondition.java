package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class DistanceCondition extends Condition implements TargetCondition, LocationCondition {

    private final RangeData requiredDistance;

    public DistanceCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        this.requiredDistance = new RangeData(configurationSection.getString("distance"));
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return requiredDistance.isInRange(entity.getLocation().distance(target.getLocation()));
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        return requiredDistance.isInRange(entity.getLocation().distance(location));
    }
}
