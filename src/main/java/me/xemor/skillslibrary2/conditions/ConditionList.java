package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.Mode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConditionList implements Iterable<Condition> {

    private List<Condition> conditions = new ArrayList<>();

    public ConditionList(ConfigurationSection conditionsSection) {
        loadConditions(conditionsSection);
    }

    public ConditionList() {}

    private void loadConditions(ConfigurationSection conditionsSection) {
        if (conditionsSection == null) return;
        Map<String, Object> values = conditionsSection.getValues(false);
        for (Object item : values.values()) {
            if (item instanceof ConfigurationSection) {
                ConfigurationSection conditionSection = (ConfigurationSection) item;
                int condition = Conditions.getCondition(conditionSection.getString("type"));
                if (condition == -1) {
                    Bukkit.getLogger().warning("Invalid Condition Type at " + conditionSection.getCurrentPath() + ".type");
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
            if (condition instanceof EntityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                EntityCondition entityCondition = (EntityCondition) condition;
                boolean result = entityCondition.isTrue(entity);
                if (!result) return false;
            }
            if (condition instanceof TargetCondition && otherObject instanceof Entity && condition.getMode().runs(Mode.OTHER)) {
                TargetCondition targetCondition = (TargetCondition) condition;
                boolean result = targetCondition.isTrue(entity, (Entity) objects[0]);
                if (!result) return false;
            }
            else if (condition instanceof LocationCondition && otherObject instanceof Location && condition.getMode().runs(Mode.LOCATION)) {
                LocationCondition locationCondition = (LocationCondition) condition;
                boolean result = locationCondition.isTrue(entity, (Location) objects[0]);
                if (!result) return false;
            }
        }
        return true;
    }

    public boolean ORConditions(Entity entity, boolean exact, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Condition condition : conditions) {
            if (condition instanceof EntityCondition && condition.getMode().runs(Mode.SELF) && (!exact || otherObject == null)) {
                EntityCondition entityCondition = (EntityCondition) condition;
                boolean result = entityCondition.isTrue(entity);
                if (result) return true;
            }
            if (condition instanceof TargetCondition && otherObject instanceof Entity && condition.getMode().runs(Mode.OTHER)) {
                TargetCondition targetCondition = (TargetCondition) condition;
                boolean result = targetCondition.isTrue(entity, (Entity) objects[0]);
                if (result) return true;
            }
            else if (condition instanceof LocationCondition && otherObject instanceof Location && condition.getMode().runs(Mode.LOCATION)) {
                LocationCondition locationCondition = (LocationCondition) condition;
                boolean result = locationCondition.isTrue(entity, (Location) objects[0]);
                if (!result) return true;
            }
        }
        return false;
    }

    public void addCondition(Condition condition) {
        conditions.add(0, condition);
    }

    @NotNull
    @Override
    public Iterator<Condition> iterator() {
        return conditions.iterator();
    }
}
