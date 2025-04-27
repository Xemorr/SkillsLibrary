package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.RangeData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class LightCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    @JsonPropertyWithDefault
    @JsonAlias("checkNaturalLight")
    private boolean checkNatural = true;
    @JsonPropertyWithDefault
    @JsonAlias("checkBlockLight")
    private boolean checkBlocks = true;
    @JsonPropertyWithDefault
    @JsonAlias("light")
    private RangeData lightRange = new RangeData();

    @Override
    public boolean isTrue(Execution execution, Entity entity) {
        return isLight(entity.getLocation());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return isTrue(execution, entity, target.getLocation());
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> isLight(location));
    }

    public boolean isLight(Location location) {
        Block blockAt = location.getWorld().getBlockAt(location);
        byte light;
        if (checkNatural && checkBlocks) {
            light = blockAt.getLightLevel();
        }
        else if (checkNatural) {
            light = blockAt.getLightFromSky();
        } else if (checkBlocks) {
            light = blockAt.getLightFromBlocks();
        }
        else {
            return true;
        }
        return lightRange.isInRange(light);
    }
}
