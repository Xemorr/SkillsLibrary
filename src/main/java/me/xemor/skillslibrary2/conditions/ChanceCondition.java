package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class ChanceCondition extends Condition implements EntityCondition {

    private double chance;

    public ChanceCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        chance = configurationSection.getDouble("chance", 1.0);
    }

    @Override
    public boolean isTrue(Entity boss) {
        return Math.random() <= chance;
    }
}
