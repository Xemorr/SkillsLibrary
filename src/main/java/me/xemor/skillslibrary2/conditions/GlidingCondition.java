package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.CompletableFuture;

public class GlidingCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    @JsonAlias("glide")
    private boolean shouldGlide = true;

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.isGliding() == shouldGlide;
        }
        return false;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
