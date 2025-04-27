package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class ChanceCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private Expression chance = new Expression(1.0);

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return ThreadLocalRandom.current().nextFloat() <= chance.result(execution, entity);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return CompletableFuture.completedFuture(ThreadLocalRandom.current().nextFloat() <= chance.result(execution, entity, target));
    }
}
