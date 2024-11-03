package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class ItemWrapperCondition extends WrapperCondition implements ComplexEntityCondition, TargetCondition {

    private EquipmentSlot equipmentSlot;
    private int slot = -1;

    public ItemWrapperCondition(int effect, ConfigurationSection configurationSection) {
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
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity) {
        if (equipmentSlot != null) {
            if (entity instanceof LivingEntity livingEntity) {
                EntityEquipment entityEquipment = livingEntity.getEquipment();
                if (entityEquipment == null) return CompletableFuture.completedFuture(false);
                ItemStack item = entityEquipment.getItem(equipmentSlot);
                return handleConditions(execution, entity, item);
            }
        }
        else if (slot != -1) {
            if (entity instanceof Player player) {
                return handleConditions(execution, entity, player.getInventory().getItem(slot));
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
