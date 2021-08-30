package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class FlyingCondition extends Condition implements EntityCondition, TargetCondition {
    public FlyingCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.isFlying();
        }
        return false;
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (target instanceof Player) {
            Player player = (Player) entity;
            return player.isFlying();
        }
        return false;
    }
}
