package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.CompletableFuture;

public class HealthCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private RangeData healthPercentage = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            double currentHealthPercentage = (livingEntity.getHealth() / livingEntity.getAttribute(Attribute.MAX_HEALTH).getValue()) * 100;
            return healthPercentage.isInRange(currentHealthPercentage);
        }
        return false;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }


}
