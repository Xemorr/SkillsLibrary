package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

public class HotbarSlotCondition extends Condition implements EntityCondition {

    @JsonPropertyWithDefault
    private RangeData slot;

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof HumanEntity humanEntity) {
            return slot.isInRange(humanEntity.getInventory().getHeldItemSlot());
        }
        return false;
    }
}
