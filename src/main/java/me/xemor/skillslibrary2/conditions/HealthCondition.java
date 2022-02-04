package me.xemor.skillslibrary2.conditions;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class HealthCondition extends Condition implements EntityCondition, TargetCondition {

    private final double maximumHealth;
    private final double minimumHealth;

    public HealthCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        minimumHealth = configurationSection.getDouble("minimumHealthPercentage", 0) / 100;
        maximumHealth = configurationSection.getDouble("maximumHealthPercentage", 100) / 100;
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            double healthPercentage = livingEntity.getHealth() / livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            return healthPercentage >= minimumHealth && healthPercentage <= maximumHealth;
        }

        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            double healthPercentage = livingEntity.getHealth() / livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            return healthPercentage >= minimumHealth && healthPercentage <= maximumHealth;
        }
        return false;
    }


}
