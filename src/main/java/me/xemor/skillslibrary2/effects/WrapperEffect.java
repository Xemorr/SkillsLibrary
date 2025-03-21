package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public abstract class WrapperEffect extends Effect {

    @JsonPropertyWithDefault
    private ConditionList conditions = new ConditionList();
    @JsonPropertyWithDefault
    private EffectList effects = new EffectList();

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
