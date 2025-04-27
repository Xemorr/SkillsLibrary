package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.concurrent.CompletableFuture;

public class SpeedCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    @JsonAlias("speed")
    private RangeData speedRange;

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        double speed = entity.getVelocity().distance(new Vector(0, 0, 0));
        return this.speedRange.isInRange(speed);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, target));
    }
}
