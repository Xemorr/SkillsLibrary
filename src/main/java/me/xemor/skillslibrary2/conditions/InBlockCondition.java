package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class InBlockCondition extends Condition implements EntityCondition, TargetCondition {

    @JsonPropertyWithDefault
    private SetData<Material> blocks = new SetData<>();

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        return inBlock(boss);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> inBlock(target));
    }

    public boolean inBlock(Entity entity) {
        return blocks.inSet(entity.getLocation().getBlock().getType());
    }
}
