package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.Map;

public abstract class ModifyEffect extends Effect {

    @JsonPropertyWithDefault
    private Operation operation = Operation.SET;
    @JsonPropertyWithDefault
    protected Expression value = new Expression(1);

    public double changeValue(Execution execution, double oldValue) {
        return changeValue(execution, oldValue, Map.of());
    }

    public double changeValue(Execution execution, double oldValue, Entity entity) {
        return changeValue(execution, oldValue, Map.of("self", entity));
    }

    public double changeValue(Execution execution, double oldValue, Entity entity, Entity other) {
        return changeValue(execution, oldValue, Map.of("self", entity, "other", other));
    }

    public double changeValue(Execution execution, double oldValue, Map<String, PersistentDataHolder> modeToHolder) {
        return switch (operation) {
            case ADD -> oldValue + value.result(execution, modeToHolder);
            case SUBTRACT -> oldValue - value.result(execution, modeToHolder);
            case MULTIPLY -> oldValue * value.result(execution, modeToHolder);
            case DIVIDE -> oldValue / value.result(execution, modeToHolder);
            case SET -> value.result(execution, modeToHolder);
        };
    }

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, SET;
    }

    public Operation getOperation() {
        return operation;
    }
}
