package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.InventorySlot;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.ItemComparisonData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.concurrent.CompletableFuture;

public class ItemCondition extends Condition implements EntityCondition, TargetCondition, ItemStackCondition {

    @JsonPropertyWithDefault
    private InventorySlot slot = new InventorySlot(EquipmentSlot.HAND);
    @CompulsoryJsonProperty
    @JsonAlias("item")
    private ItemComparisonData itemComparison;

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
            if (slot.getEquipmentSlot() != null) item = livingEntity.getEquipment().getItem(slot.getEquipmentSlot());
            else if (entity instanceof Player player) {
                PlayerInventory inventory = player.getInventory();
                item = inventory.getItem(slot.getSlot());
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
