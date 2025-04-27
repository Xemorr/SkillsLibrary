package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;

import java.util.concurrent.CompletableFuture;

public class InInventoryCondition extends Condition implements EntityCondition, TargetCondition {

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        return isInInventory(boss);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isInInventory(target));
    }

    public boolean isInInventory(Entity entity) {
        if (entity instanceof HumanEntity humanEntity) {
            return humanEntity.getOpenInventory().getType() == InventoryType.CRAFTING;
        }
        return false;
    }
}
