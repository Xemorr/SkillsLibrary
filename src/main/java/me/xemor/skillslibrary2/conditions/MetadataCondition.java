package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.CompletableFuture;

public class MetadataCondition extends ComparisonCondition implements EntityCondition, TargetCondition {

    private NamespacedKey variable;

    @JsonCreator
    public MetadataCondition(@JsonProperty("variable") String variableStr) {
        if (variableStr == null) {
            SkillsLibrary.getInstance().getLogger().severe("You need to specify a variable name! .variable is null!");
        }
        else {
            variable = new NamespacedKey(SkillsLibrary.getInstance(), variableStr);
        }
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return isTrue(execution, entity.getPersistentDataContainer());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target.getPersistentDataContainer()));
    }

    public boolean isTrue(Execution execution, PersistentDataContainer container) {
        Double value = container.get(variable, PersistentDataType.DOUBLE);
        return checkComparison(execution, value == null ? 0 : value);
    }

}
