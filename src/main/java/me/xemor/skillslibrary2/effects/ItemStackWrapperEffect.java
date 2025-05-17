package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.InventorySlot;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapperEffect extends WrapperEffect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    @JsonAlias("equipmentSlot")
    private InventorySlot slot = new InventorySlot(EquipmentSlot.HAND);

    @Override
    public void useEffect(Execution execution, Entity entity) {
        handleEffects(execution, entity);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        handleEffects(execution, target);
    }

    public void handleEffects(Execution execution, Entity entity) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            if (entity instanceof LivingEntity livingEntity) {
                ItemStack item = null;
                if (slot.getEquipmentSlot() != null) {
                    item = livingEntity.getEquipment().getItem(slot.getEquipmentSlot());
                }
                else if (slot.getSlot() == -1) {
                    item = livingEntity.getEquipment().getItemInMainHand();
                }
                else if (entity instanceof Player player) {
                    item = player.getInventory().getItem(slot.getSlot());
                }
                if (item != null) {
                    handleEffects(execution, entity, item);
                }
            }
        });
    }
}
