package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SneakCondition extends Condition implements EntityCondition, TargetCondition {

    private final boolean sneak;

    public SneakCondition(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        sneak = configurationSection.getBoolean("sneak", true);
    }

    @Override
    public boolean isTrue(Entity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            return player.isSneaking() == sneak;
        }
        return true;
    }

    @Override
    public boolean isTrue(Entity livingEntity, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.isSneaking() == sneak;
        }
        return true;
    }
}
