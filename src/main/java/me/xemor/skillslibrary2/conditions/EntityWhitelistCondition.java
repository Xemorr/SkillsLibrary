package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EntityWhitelistCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private boolean whitelist = true;
    @JsonPropertyWithDefault
    private Set<EntityType> entities = new HashSet<>();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        boolean contained = entities.contains(entity.getType());
        return whitelist == contained;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
