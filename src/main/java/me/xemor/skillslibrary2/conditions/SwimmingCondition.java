package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SwimmingCondition extends Condition implements EntityCondition, TargetCondition {
    public SwimmingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof Player player) {
            return player.isSwimming();
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isTrue(target);
    }
}
