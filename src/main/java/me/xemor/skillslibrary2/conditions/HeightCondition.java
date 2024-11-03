package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class HeightCondition extends Condition implements EntityCondition, TargetCondition {

    private RangeData heightRange;

    public HeightCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        heightRange = new RangeData("height", configurationSection);
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return heightRange.isInRange(entity.getLocation().getY());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
            heightRange.isInRange(target.getLocation().getY());
        });
    }
}
