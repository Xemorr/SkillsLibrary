package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GlidingCondition extends Condition implements EntityCondition {

    private final boolean shouldGlide;

    public GlidingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        shouldGlide = configurationSection.getBoolean("shouldGlide", true) && configurationSection.getBoolean("glide", true);
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            return livingEntity.isGliding() == shouldGlide;
        }
        return false;
    }
}
