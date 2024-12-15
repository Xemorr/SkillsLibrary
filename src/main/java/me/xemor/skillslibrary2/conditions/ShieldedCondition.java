package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ShieldedCondition extends Condition implements EntityCondition {

    public ShieldedCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof Player player) {
            return player.isBlocking();
        }
        return false;
    }
}
