package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ORCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition, ItemStackCondition {

    private final ConditionList conditions;

    public ORCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        conditions = new ConditionList(configurationSection.getConfigurationSection("conditions"));
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        try {
            return conditions.ORConditions(execution, entity, true).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, ItemStack itemStack) {
        return conditions.ORConditions(execution, entity, true, itemStack);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return conditions.ORConditions(execution, entity, true, location);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return conditions.ORConditions(execution, entity, true, target);
    }

}
