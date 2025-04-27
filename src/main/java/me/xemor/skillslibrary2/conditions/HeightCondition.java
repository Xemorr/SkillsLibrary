package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class HeightCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private RangeData height = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return height.isInRange(entity.getLocation().getY());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
            height.isInRange(target.getLocation().getY());
        });
    }
}
