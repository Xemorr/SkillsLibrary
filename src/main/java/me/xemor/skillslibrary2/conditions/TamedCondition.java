package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

import java.util.concurrent.CompletableFuture;

public class TamedCondition extends Condition implements TargetCondition {

    @JsonPropertyWithDefault
    private boolean checkOwner = true;

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        if (target instanceof Tameable tameable) {
            if (checkOwner) return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> entity.equals(tameable.getOwner()));
            else return SkillsLibrary.getFoliaHacks().runASAP(tameable, tameable::isTamed);
        }
        return CompletableFuture.completedFuture(false);
    }

}
