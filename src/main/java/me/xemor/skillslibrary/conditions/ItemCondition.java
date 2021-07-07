package me.xemor.skillslibrary.conditions;

import me.xemor.configurationdata.ItemStackData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemCondition extends Condition implements EntityCondition, TargetCondition {

    private EquipmentSlot equipmentSlot;
    private ItemStack item;

    public ItemCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        String equipmentSlotStr = configurationSection.getString("slot", "HAND").toUpperCase();
        try {
            equipmentSlot = EquipmentSlot.valueOf(equipmentSlotStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid equipment slot! " + configurationSection.getCurrentPath() + ".slot");
            return;
        }
        ConfigurationSection itemSection = configurationSection.getConfigurationSection("item");
        if (itemSection != null) {
            item = new ItemStackData(itemSection).getItem();
        }
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        ItemStack item = boss.getEquipment().getItem(equipmentSlot);
        return false;
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return false;
    }
}
