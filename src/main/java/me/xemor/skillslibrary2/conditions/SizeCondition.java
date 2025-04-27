package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Slime;

import java.util.concurrent.CompletableFuture;

public class SizeCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private RangeData size = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return checkCondition(entity);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> checkCondition(target));
    }

    public boolean checkCondition(Entity entity) {
        if (entity instanceof Slime slime) {
            return size.isInRange(slime.getSize());
        }
        if (entity instanceof Phantom phantom) {
            return size.isInRange(phantom.getSize());
        }
        return false;
    }

}
