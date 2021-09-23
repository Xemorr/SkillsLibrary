package me.xemor.skillslibrary2.effects;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface ItemStackEffect {

    boolean useEffect(Entity entity, ItemStack item);

}
