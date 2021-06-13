package me.xemor.skillslibrary.conditions;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class HealthCondition extends Condition implements EntityCondition {

    private final double maximumHealth;
    private final double minimumHealth;

    public HealthCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumHealth = configurationSection.getDouble("minimumHealthPercentage", 0) / 100;
        maximumHealth = configurationSection.getDouble("maximumHealthPercentage", 100) / 100;
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        double healthPercentage = boss.getHealth() / boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        return healthPercentage >= minimumHealth && healthPercentage <= maximumHealth;
    }


}
