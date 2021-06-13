package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public class GlidingCondition extends Condition implements EntityCondition {

    private final boolean shouldGlide;

    public GlidingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        shouldGlide = configurationSection.getBoolean("shouldGlide", true);
    }

    @Override
    public boolean isTrue(LivingEntity livingEntity) {
        return livingEntity.isGliding() == shouldGlide;
    }
}
