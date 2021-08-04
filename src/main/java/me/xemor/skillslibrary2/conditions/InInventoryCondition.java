package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;

public class InInventoryCondition extends Condition implements EntityCondition, TargetCondition {
    public InInventoryCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity boss) {
        return isInInventory(boss);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return isInInventory(target);
    }

    public boolean isInInventory(Entity entity) {
        if (entity instanceof HumanEntity) {
            HumanEntity humanEntity = (HumanEntity) entity;
            return humanEntity.getOpenInventory().getType() == InventoryType.CRAFTING;
        }
        return false;
    }
}
