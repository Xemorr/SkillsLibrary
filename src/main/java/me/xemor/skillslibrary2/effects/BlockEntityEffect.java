package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class BlockEntityEffect extends Effect implements TargetEffect {

    @JsonPropertyWithDefault
    private Expression durationInTicks = new Expression(-1);
    @JsonPropertyWithDefault
    private Material blockToPlace = Material.COBWEB;

    @JsonCreator
    public BlockEntityEffect(Expression duration, Material blockToPlace) {
        if (duration != null) this.durationInTicks = duration.apply((result) -> result * 20);
        if (blockToPlace != null) this.blockToPlace = blockToPlace;
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        Location location = target.getLocation();
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        Material originalType = block.getType();
        block.setType(blockToPlace, false);
        double calculatedDurationInTicks = durationInTicks.result(execution, entity, target);
        if (calculatedDurationInTicks >= 0) {
            SkillsLibrary.getScheduling().entitySpecificScheduler(target).runDelayed(() -> {
                    block.setType(originalType, false);
            }, () -> {}, Math.round(calculatedDurationInTicks));
        }
    }
}
