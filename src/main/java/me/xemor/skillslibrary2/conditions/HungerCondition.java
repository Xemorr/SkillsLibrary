package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

import java.util.concurrent.CompletableFuture;

public class HungerCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private RangeData foodLevel = new RangeData();
    @JsonPropertyWithDefault
    private RangeData saturation = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        if (entity instanceof HumanEntity humanEntity) {
            int currentFoodLevel = humanEntity.getFoodLevel();
            boolean isFoodLevelInRange = foodLevel.isInRange(currentFoodLevel);

            float currentSaturation = humanEntity.getSaturation();
            boolean isSaturationInRange = saturation.isInRange(currentSaturation);

            return isFoodLevelInRange && isSaturationInRange;
        }
        return false;
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }


}
