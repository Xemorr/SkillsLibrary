package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.ThreadLocalRandom;

public class ChanceCondition extends Condition implements EntityCondition {

    private final double chance;

    public ChanceCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        chance = configurationSection.getDouble("chance", 1.0);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return ThreadLocalRandom.current().nextFloat() <= chance;
    }
}
