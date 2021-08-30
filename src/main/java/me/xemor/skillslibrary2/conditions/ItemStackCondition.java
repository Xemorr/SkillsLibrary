package me.xemor.skillslibrary2.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface ItemStackCondition {

    boolean isTrue(Entity entity, ItemStack itemStack);

}
