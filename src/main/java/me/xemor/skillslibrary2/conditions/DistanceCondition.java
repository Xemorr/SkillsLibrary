package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class DistanceCondition extends Condition implements TargetCondition {

    private RangeData requiredDistanceSquared;

    public DistanceCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        this.requiredDistanceSquared = new RangeData(configurationSection.getString("distance"));
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return requiredDistanceSquared.isInRange(entity.getLocation().distanceSquared(target.getLocation()));
    }
}
