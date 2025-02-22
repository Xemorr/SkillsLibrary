package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class NOTCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition, ItemStackCondition {

    private Condition condition;

    public NOTCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        ConfigurationSection conditionSection = configurationSection.getConfigurationSection("condition");
        if (conditionSection == null) {
            SkillsLibrary.getInstance().getLogger().severe("You haven't specified a condition for the NOT condition " + configurationSection.getCurrentPath());
            return;
        }
        String conditionTypeStr = conditionSection.getString("type", "");
        int conditionType = Conditions.getCondition(conditionTypeStr);
        this.condition = Condition.create(conditionType, conditionSection);
    }


    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (condition instanceof EntityCondition entityCondition) {
            return !entityCondition.isTrue(execution, entity);
        }
        return true;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        if (condition instanceof TargetCondition targetCondition) {
            return targetCondition.isTrue(execution, entity, target).thenApply((b) -> !b);
        }
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, ItemStack itemStack) {
        if (condition instanceof ItemStackCondition itemCondition) {
            return itemCondition.isTrue(execution, entity, itemStack).thenApply((b) -> !b);
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        if (condition instanceof LocationCondition locationCondition) {
            return locationCondition.isTrue(execution, entity, location).thenApply((b) -> !b);
        }
        return CompletableFuture.completedFuture(false);
    }
}
