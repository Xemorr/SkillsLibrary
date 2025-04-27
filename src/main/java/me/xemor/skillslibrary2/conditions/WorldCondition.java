package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class WorldCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    @JsonPropertyWithDefault
    private SetData<String> worlds = new SetData<>();

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> worlds.inSet(location.getWorld().getName()));
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return worlds.inSet(entity.getWorld().getName());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> worlds.inSet(target.getWorld().getName()));
    }
}
