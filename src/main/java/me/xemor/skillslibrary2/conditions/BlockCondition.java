package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;


public class BlockCondition extends Condition implements LocationCondition {

    @JsonPropertyWithDefault
    @JsonAlias("materials")
    private SetData<Material> allowedMaterials = new SetData<>();

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Location location) {
        World world = location.getWorld();
        return SkillsLibrary.getFoliaHacks().runASAP(location, () -> allowedMaterials.inSet(world.getBlockAt(location).getType()));
    }

}
