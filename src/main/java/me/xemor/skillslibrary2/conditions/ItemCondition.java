package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.ItemComparisonData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.concurrent.CompletableFuture;

public class ItemCondition extends Condition implements EntityCondition, TargetCondition, ItemStackCondition {

    private EquipmentSlot equipmentSlot;
    private int slot;
    private ItemComparisonData itemComparison;

    public ItemCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        String equipmentSlotStr = configurationSection.getString("slot", "HAND").toUpperCase();
        try {
            equipmentSlot = EquipmentSlot.valueOf(equipmentSlotStr);
        } catch (IllegalArgumentException e) {
            try {
                slot = Integer.parseInt(equipmentSlotStr);
            } catch (NumberFormatException ignored) {
                SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid equipment slot! " + configurationSection.getCurrentPath() + ".slot");
            }
        }
        ConfigurationSection itemSection = configurationSection.getConfigurationSection("item");
        if (itemSection != null) {
            itemComparison = new ItemComparisonData(itemSection);
        }
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return matches(entity);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> matches(target));
    }

    public boolean matches(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack item = null;
            if (equipmentSlot != null) item = livingEntity.getEquipment().getItem(equipmentSlot);
            else if (entity instanceof Player player) {
                PlayerInventory inventory = player.getInventory();
                item = inventory.getItem(slot);
            }
            if (item == null) item = new ItemStack(Material.AIR);
            return itemComparison.matches(item);
        }
        return false;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, ItemStack itemStack) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            itemComparison.matches(itemStack);
        });
    }
}
