package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapperEffect extends WrapperEffect implements EntityEffect, TargetEffect {

    private EquipmentSlot equipmentSlot;
    private int slot;

    public ItemStackWrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
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
    }

    @Override
    public boolean useEffect(Entity entity) {
        handleEffects(entity);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        handleEffects(target);
        return false;
    }

    public void handleEffects(Entity entity) {
        if (entity instanceof LivingEntity) {
            ItemStack item = null;
            LivingEntity livingEntity = (LivingEntity) entity;
            if (equipmentSlot != null) {
                item = livingEntity.getEquipment().getItem(equipmentSlot);
            }
            else if (slot == -1) {
                item = livingEntity.getEquipment().getItemInMainHand();
            }
            else if (entity instanceof Player) {
                Player player = (Player) entity;
                item = player.getInventory().getItem(slot);
            }
            if (item != null) {
                handleEffects(entity, item);
            }
        }
    }
}
