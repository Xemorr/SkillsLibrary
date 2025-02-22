package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public abstract class WrapperEffect extends Effect {

    private final ConditionList conditions;
    private final EffectList effects;

    public WrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection conditionSection = configurationSection.getConfigurationSection("conditions");
        if (conditionSection != null) {
            conditions = new ConditionList(conditionSection);
        }
        else {
            conditions = new ConditionList();
        }
        ConfigurationSection effectsSection = configurationSection.getConfigurationSection("effects");
        if (effectsSection != null) {
            effects = new EffectList(effectsSection);
        }
        else {
            effects = new EffectList();
        }
    }

    public void handleEffects(Execution execution, Entity entity, Object... objects) {
        conditions.ANDConditions(execution, entity, false, objects).thenApply((b) -> {
            if (b) effects.handleExactEffects(execution, entity, objects);
            return b;
        });
    }

    public ConditionList getConditions() {
        return conditions;
    }

    public EffectList getEffects() {
        return effects;
    }
}
