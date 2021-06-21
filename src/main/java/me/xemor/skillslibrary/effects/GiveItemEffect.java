package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.ItemStackData;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GiveItemEffect extends Effect implements EntityEffect, TargetEffect {

    private final ItemStack item;
    private final boolean dropIfFull;

    public GiveItemEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection item = configurationSection.getConfigurationSection("item");
        this.item = new ItemStackData(item).getItem();
        dropIfFull = configurationSection.getBoolean("dropIfFull", true);
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        giveItem(entity);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        giveItem(entity);
        return false;
    }

    public void giveItem(Entity entity) {
        if (entity instanceof InventoryHolder) {
            InventoryHolder inventoryHolder = (InventoryHolder) entity;
            Inventory inventory = inventoryHolder.getInventory();
            HashMap<Integer, ItemStack> leftovers = inventory.addItem(item);
            if (dropIfFull) {
                World world = entity.getWorld();
                for (ItemStack item : leftovers.values()) {
                    world.dropItem(entity.getLocation(), item);
                }
            }
        }
    }
}
