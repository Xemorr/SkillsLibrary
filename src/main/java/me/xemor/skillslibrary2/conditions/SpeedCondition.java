package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class SpeedCondition extends Condition implements EntityCondition, TargetCondition {

    private final RangeData speedRange;

    public SpeedCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        speedRange = new RangeData("speed", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        double speed = entity.getVelocity().distance(new Vector(0, 0, 0));
        return speedRange.isInRange(speed);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(target);
    }
}
