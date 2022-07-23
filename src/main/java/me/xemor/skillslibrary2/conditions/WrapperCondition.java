package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.effects.Effect;
import me.xemor.skillslibrary2.effects.EffectList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public abstract class WrapperCondition extends Condition {

    private final ConditionList conditions;

    public WrapperCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        ConfigurationSection conditionSection = configurationSection.getConfigurationSection("conditions");
        if (conditionSection != null) {
            conditions = new ConditionList(conditionSection);
        }
        else {
            conditions = new ConditionList();
        }
    }

    public boolean handleConditions(Entity entity, Object... objects) {
        return conditions.ANDConditions(entity, false, objects);
    }

    public ConditionList getConditions() {
        return conditions;
    }
}
