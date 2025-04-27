package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.InventorySlot;
import me.xemor.configurationdata.JsonPropertyWithDefault;
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

    @JsonPropertyWithDefault
    private InventorySlot slot = new InventorySlot(EquipmentSlot.HAND);

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity) {
        if (slot.getEquipmentSlot() != null) {
            if (entity instanceof LivingEntity livingEntity) {
                EntityEquipment entityEquipment = livingEntity.getEquipment();
                if (entityEquipment == null) return CompletableFuture.completedFuture(false);
                ItemStack item = entityEquipment.getItem(slot.getEquipmentSlot());
                return handleConditions(execution, entity, item);
            }
        }
        else if (slot.getSlot() != -1) {
            if (entity instanceof Player player) {
                return handleConditions(execution, entity, player.getInventory().getItem(slot.getSlot()));
            }
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
