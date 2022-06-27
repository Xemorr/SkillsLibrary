package me.xemor.skillslibrary2.effects;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import me.xemor.configurationdata.comparison.SetData;

public class BonemealEffect extends Effect implements EntityEffect, LocationEffect, TargetEffect {

    private Set<BlockFace> blockFaces;

    public BonemealEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        blockFaces = new SetData<>(BlockFace.class, "faces", configurationSection).getSet();
        if (blockFaces.size() == 0) {
            blockFaces = Set.of(BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST);
        }
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        for (BlockFace face : blockFaces) {
            boolean success = location.getBlock().applyBoneMeal(face);
            if (success) break;
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        useEffect(entity, entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, entity.getLocation());
        return false;
    }

}
