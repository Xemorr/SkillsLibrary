package me.xemor.skillslibrary.conditions;

import me.xemor.skillslibrary.effects.EntityEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class InInventoryCondition extends Condition implements EntityCondition, TargetCondition {
    public InInventoryCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return false;
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return false;
    }

    public boolean isInInventory(Entity entity) {
        if (entity instanceof HumanEntity) {
            Player player = (Player) entity;
        }
        return true;
    }
}
