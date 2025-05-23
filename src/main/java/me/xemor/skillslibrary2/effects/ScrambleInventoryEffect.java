package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class ScrambleInventoryEffect extends Effect implements EntityEffect, TargetEffect {

    @Override
    public void useEffect(Execution execution, Entity entity) {
        scrambleInventory(entity);
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        scrambleInventory(target);
    }

    public void scrambleInventory(Entity entity) {
        if (entity instanceof InventoryHolder inventoryHolder) {
            Inventory inventory = inventoryHolder.getInventory();
            ItemStack[] contents = inventory.getStorageContents();
            Collections.shuffle(Arrays.asList(contents));
            inventory.setStorageContents(contents);
        }
    }
}
