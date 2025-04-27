package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CooldownCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private Expression cooldown = new Expression(10);
    @JsonIgnore
    private final HashMap<UUID, Long> cooldownEndsMap = new HashMap<>();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        double cooldownEnds = cooldownEndsMap.getOrDefault(entity.getUniqueId(), -1L);
        if (cooldownEnds < System.currentTimeMillis()) {
            cooldownEndsMap.put(entity.getUniqueId(), ((long) (cooldown.result(execution, entity) * 1000)) + System.currentTimeMillis());
            return true;
        }
        execution.setValue("current_cooldown", cooldownEnds - System.currentTimeMillis());
        return false;
    }

    @Override
    public CompletableFuture isTrue(Execution execution, Entity entity, Entity other) {
        double cooldownEnds = cooldownEndsMap.getOrDefault(other.getUniqueId(), -1L);
        if (cooldownEnds < System.currentTimeMillis()) {
            cooldownEndsMap.put(other.getUniqueId(), ((long) (cooldown.result(execution, entity, other) * 1000)) + System.currentTimeMillis());
            return CompletableFuture.completedFuture(true);
        }
        execution.setValue("current_cooldown", cooldownEnds - System.currentTimeMillis());
        return CompletableFuture.completedFuture(false);
    }
}
