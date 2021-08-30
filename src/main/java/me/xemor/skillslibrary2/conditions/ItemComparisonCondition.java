package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.ItemComparisonData;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemComparisonCondition extends Condition implements EntityCondition, TargetCondition {

    private EquipmentSlot equipmentSlot;
    private int slot;
    private ItemComparisonData itemComparison;

    public ItemComparisonCondition(int condition, ConfigurationSection configurationSection) {
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
            return;
        }
        ConfigurationSection itemSection = configurationSection.getConfigurationSection("item");
        if (itemSection != null) {
            itemComparison = new ItemComparisonData(itemSection);
        }
    }

    @Override
    public boolean isTrue(Entity entity) {
        return matches(entity);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return matches(target);
    }

    public boolean matches(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            ItemStack item = null;
            if (equipmentSlot != null) item = livingEntity.getEquipment().getItem(equipmentSlot);
            else if (entity instanceof Player) {
                Player player = (Player) entity;
                PlayerInventory inventory = player.getInventory();
                item = inventory.getItem(slot);
            }
            if (item == null) item = new ItemStack(Material.AIR);
            return itemComparison.matches(item);
        }
        return false;
    }

}
