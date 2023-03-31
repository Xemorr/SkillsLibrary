package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class HealthCondition extends Condition implements EntityCondition, TargetCondition {

    private final RangeData healthRange;

    public HealthCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        if (configurationSection.contains("healthPercentage")) {
            healthRange = new RangeData(configurationSection.getString("healthPercentage", "0 - 100"));
        }
        else {
            healthRange = new RangeData(configurationSection.getDouble("minimumHealthPercentage", 0) / 100 + " - " + configurationSection.getDouble("maximumHealthPercentage", 100) / 100);
        }
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            double healthPercentage = livingEntity.getHealth() / livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 100;
            return healthRange.isInRange(healthPercentage);
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(target);
    }


}
