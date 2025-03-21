package me.xemor.skillslibrary2.effects;

import java.util.Set;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import me.xemor.configurationdata.comparison.SetData;

public class BonemealEffect extends Effect implements EntityEffect, LocationEffect, TargetEffect {

    @JsonPropertyWithDefault
    private SetData<BlockFace> blockFaces = new SetData<>();

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        SkillsLibrary.getFoliaHacks().runASAP(location, () -> {
            for (BlockFace face : BlockFace.values()) {
                if (blockFaces.inSet(face)) {
                    boolean success = location.getBlock().applyBoneMeal(face);
                    if (success) break;
                }
            }
        });
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        useEffect(execution, entity, entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        useEffect(execution, entity, target.getLocation());
    }

}
