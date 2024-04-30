package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

public class HotbarSlotCondition extends Condition implements EntityCondition {

    private RangeData slotRange;

    public HotbarSlotCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        slotRange = new RangeData(configurationSection.getString("slot"));
    }

    @Override
    public boolean isTrue(Entity entity) {
        if (entity instanceof HumanEntity humanEntity) {
            return slotRange.isInRange(humanEntity.getInventory().getHeldItemSlot());
        }
        return false;
    }
}
