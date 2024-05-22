package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SprintingCondition extends Condition implements EntityCondition, TargetCondition {
    public SprintingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof Player player) {
            return player.isSprinting();
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(target);
    }
}
