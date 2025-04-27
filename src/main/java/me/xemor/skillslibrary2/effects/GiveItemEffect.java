package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GiveItemEffect extends Effect implements EntityEffect, TargetEffect {

    @CompulsoryJsonProperty
    private ItemStack item;
    @JsonPropertyWithDefault
    private boolean dropIfFull = true;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        giveItem(entity);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> giveItem(target));
    }

    public void giveItem(Entity entity) {
        if (entity instanceof InventoryHolder inventoryHolder) {
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
