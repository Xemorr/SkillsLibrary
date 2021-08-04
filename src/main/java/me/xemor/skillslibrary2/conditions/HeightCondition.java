package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class HeightCondition extends Condition implements EntityCondition, TargetCondition {

    private RangeData heightRange;

    public HeightCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        heightRange = new RangeData("height", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        return heightRange.isInRange(entity.getLocation().getY());
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return heightRange.isInRange(target.getLocation().getY());
    }
}
