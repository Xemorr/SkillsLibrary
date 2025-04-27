package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public abstract class WrapperCondition extends Condition {

    @JsonPropertyWithDefault
    private ConditionList conditions = new ConditionList();

    public CompletableFuture<Boolean> handleConditions(Execution execution, Entity entity, Object... objects) {
        return conditions.ANDConditions(execution, entity, false, objects);
    }

    public ConditionList getConditions() {
        return conditions;
    }
}
