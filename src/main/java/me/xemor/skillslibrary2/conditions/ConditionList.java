package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.Mode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConditionList implements Iterable<Condition> {

    private List<Condition> conditions = new ArrayList<>(1);

    public ConditionList(ConfigurationSection conditionsSection) {
        loadConditions(conditionsSection);
    }

    public ConditionList() {}


    private void loadConditions(ConfigurationSection conditionsSection) {
        if (conditionsSection == null) return;
        Map<String, Object> values = conditionsSection.getValues(false);
        conditions = new ArrayList<>(values.size());
        for (Object item : values.values()) {
            if (item instanceof ConfigurationSection conditionSection) {
                int condition = Conditions.getCondition(conditionSection.getString("type"));
                if (condition == -1) {
                    Bukkit.getLogger().warning("Invalid Condition Type at " + conditionSection.getCurrentPath() + ".type");
                    continue;
                }
                Condition conditionData = Condition.create(condition, conditionSection);
                if (conditionData != null) {
                    conditions.add(conditionData);
                }
            }
        }
    }

    @Deprecated
    public boolean areConditionsTrue(Entity entity, Object... objects) {
        return ANDConditions(entity, false, objects);
    }

    public boolean ANDConditions(Entity entity, boolean exact, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Condition condition : conditions) {
            if (condition instanceof EntityCondition entityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                boolean result = entityCondition.isTrue(entity);
                if (!result) { condition.getOtherwise().handleEffects(entity); return false; }
            }
            if (condition instanceof TargetCondition targetCondition && otherObject instanceof Entity other && condition.getMode().runs(Mode.OTHER)) {
                boolean result = targetCondition.isTrue(entity, other);
                if (!result) { condition.getOtherwise().handleEffects(entity, other); return false; }
            }
            else if (condition instanceof LocationCondition locationCondition && otherObject instanceof Location location && condition.getMode().runs(Mode.LOCATION)) {
                boolean result = locationCondition.isTrue(entity, location);
                if (!result) { condition.getOtherwise().handleEffects(entity, location); return false; }
            }
            else if (condition instanceof ItemStackCondition itemStackCondition && otherObject instanceof ItemStack item && condition.getMode().runs(Mode.ITEM)) {
                boolean result = itemStackCondition.isTrue(entity, item);
                if (!result) { condition.getOtherwise().handleEffects(entity, item); return false; }
            }
        }
        return true;
    }

    public boolean ORConditions(Entity entity, boolean exact, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Condition condition : conditions) {
            if (condition instanceof EntityCondition entityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                boolean result = entityCondition.isTrue(entity);
                if (result) return true;
            }
            if (condition instanceof TargetCondition targetCondition && otherObject instanceof Entity other && condition.getMode().runs(Mode.OTHER)) {
                boolean result = targetCondition.isTrue(entity, other);
                if (result) return true;
            }
            else if (condition instanceof LocationCondition locationCondition && otherObject instanceof Location location && condition.getMode().runs(Mode.LOCATION)) {
                boolean result = locationCondition.isTrue(entity, location);
                if (result) return true;
            }
            else if (condition instanceof ItemStackCondition itemStackCondition && otherObject instanceof ItemStack item && condition.getMode().runs(Mode.ITEM)) {
                boolean result = itemStackCondition.isTrue(entity, item);
                if (result) return true;
            }
        }

        return false;
    }

    public void prependCondition(Condition condition) { conditions.add(0, condition); }

    public void appendCondition(Condition condition) { conditions.add(condition); }

    @Deprecated
    public void addCondition(Condition condition) {
        conditions.add(0, condition);
    }

    @NotNull
    @Override
    public Iterator<Condition> iterator() {
        return conditions.iterator();
    }
}
