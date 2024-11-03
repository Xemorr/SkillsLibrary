package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class DistanceCondition extends Condition implements TargetCondition, LocationCondition {

    private final RangeData requiredDistance;

    public DistanceCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        this.requiredDistance = new RangeData("distance", configurationSection);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> isTrue(execution, entity, target.getLocation()));
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            Location entityLocation = entity.getLocation();
            future.complete(requiredDistance.isInRange(entityLocation.distance(location)));
        });
        return future;
    }
}
