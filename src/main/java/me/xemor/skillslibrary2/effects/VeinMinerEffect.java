package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.Duration;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.EnumSet;

public class VeinMinerEffect extends Effect implements ComplexLocationEffect {

    @JsonPropertyWithDefault
    @JsonAlias("types")
    private SetData<Material> materials = new SetData<>();
    @JsonPropertyWithDefault
    private Duration delay = new Duration(0.05D);
    @JsonPropertyWithDefault
    private int limit = 10; // The maximum number of iterations outwards from the centre NOT the number of blocks broken
    @JsonPropertyWithDefault
    private boolean allowMultiTypeVein = false;

    private final static EnumSet<BlockFace> faces = EnumSet.complementOf(
            EnumSet.of(BlockFace.EAST_NORTH_EAST, BlockFace.EAST_SOUTH_EAST,
            BlockFace.NORTH_NORTH_EAST, BlockFace.NORTH_NORTH_WEST,
            BlockFace.SOUTH_SOUTH_EAST, BlockFace.SOUTH_SOUTH_WEST,
            BlockFace.WEST_NORTH_WEST, BlockFace.WEST_SOUTH_WEST));

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        breakLog(location.getBlock(), 0);
    }

    private void breakLog(Block block, int count) {
        Material currentType = block.getType();
        if (count >= limit) return;
        if (materials.inSet(currentType)) {
            SkillsLibrary.getScheduling().regionSpecificScheduler(block.getLocation()).runDelayed(
                    () -> {
                        for (BlockFace face : faces) {
                            Block blockToBreak = block.getRelative(face);
                            if (allowMultiTypeVein || blockToBreak.getType() == currentType) {
                                breakLog(blockToBreak, count + 1);
                            }
                        }
                        block.breakNaturally();
                    }, delay.getDurationInTicks().orElse(1L)
            );
        }
    }
}
