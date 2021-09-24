package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ORCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition, ItemStackCondition {

    private final ConditionList conditions;

    public ORCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        conditions = new ConditionList(configurationSection.getConfigurationSection("conditions"));
    }

    @Override
    public boolean isTrue(Entity entity) {
        return conditions.ORConditions(entity, true);
    }

    @Override
    public boolean isTrue(Entity entity, ItemStack itemStack) {
        return conditions.ORConditions(entity, true, itemStack);
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        return conditions.ORConditions(entity, true, location);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return conditions.ORConditions(entity, true, target);
    }

}
