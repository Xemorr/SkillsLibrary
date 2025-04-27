package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class BiomeCondition extends Condition implements EntityCondition, LocationCondition, TargetCondition {

    @JsonPropertyWithDefault
    private SetData<Biome> biomes = new SetData<>();

    private boolean calculate(Entity entity, Location location) {
        return biomes.inSet(getBiome(location));
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> calculate(entity, location));
    }

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return calculate(entity, entity.getLocation());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return isTrue(execution, entity, target.getLocation());
    }

    public Biome getBiome(Location location) {
        return location.getBlock().getBiome();
    }

}
