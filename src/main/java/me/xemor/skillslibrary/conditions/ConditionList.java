package me.xemor.skillslibrary.conditions;

import me.xemor.skillslibrary.Mode;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConditionList implements Iterable<Condition> {

    ArrayList<Condition> conditions = new ArrayList<>();

    public ConditionList(ConfigurationSection conditionsSection) {
        loadConditions(conditionsSection);
    }

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

    public boolean areConditionsTrue(LivingEntity skillEntity, Object... objects) {
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Condition condition : conditions) {
            if (condition instanceof EntityCondition && condition.getMode().runs(Mode.SELF)) {
                EntityCondition entityCondition = (EntityCondition) condition;
                boolean result = entityCondition.isTrue(skillEntity);
                if (!result) return false;
            }
            if (condition instanceof TargetCondition && otherObject instanceof Entity && condition.getMode().runs(Mode.OTHER)) {
                TargetCondition targetCondition = (TargetCondition) condition;
                boolean result = targetCondition.isTrue(skillEntity, (Entity) objects[0]);
                if (!result) return false;
            }
            else if (condition instanceof BlockCondition && otherObject instanceof Block && condition.getMode().runs(Mode.BLOCK)) {
                BlockCondition blockCondition = (BlockCondition) condition;
                boolean result = blockCondition.isTrue(skillEntity, (Block) objects[0]);
                if (!result) return false;
            }
        }
        return true;
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
