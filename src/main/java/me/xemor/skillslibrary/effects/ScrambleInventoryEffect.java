package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class ScrambleInventoryEffect extends Effect implements EntityEffect, TargetEffect {
    public ScrambleInventoryEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        scrambleInventory(entity);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        scrambleInventory(entity);
        return false;
    }

    public void scrambleInventory(Entity entity) {
        if (entity instanceof InventoryHolder) {
            InventoryHolder inventoryHolder = (InventoryHolder) entity;
            Inventory inventory = inventoryHolder.getInventory();
            ItemStack[] contents = inventory.getStorageContents();
            Collections.shuffle(Arrays.asList(contents));
            inventory.setStorageContents(contents);
        }
    }
}
