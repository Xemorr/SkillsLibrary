package me.xemor.skillslibrary2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.skillslibrary2.effects.EffectList;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.triggers.TriggerData;
import org.bukkit.entity.Entity;

public class Skill {

    @JsonIgnore
    private TriggerData trigger = null;
    @JsonIgnore
    private EffectList effects = null;

    @JsonCreator
    public Skill(
            @JsonProperty("trigger") TriggerData trigger,
            @JsonProperty("effects") EffectList effects,
            @JsonProperty("conditions") ConditionList conditions
    ) {
        if (conditions != null) trigger.addConditions(conditions); // support either conditions nested in trigger, or not.
        this.trigger = trigger;
        this.effects = effects;
    }

    public boolean handleEffects(Entity entity, Object... objects) {
        Execution execution = new Execution();
        trigger.getConditions().ANDConditions(execution, entity, false, objects).thenAccept((b) -> {
            if (b) effects.handleEffects(execution, entity, objects);
        });
        return execution.isCancelled();
    }

    public TriggerData getTriggerData() {
        return trigger;
    }

    public EffectList getEffects() {
        return effects;
    }
}
