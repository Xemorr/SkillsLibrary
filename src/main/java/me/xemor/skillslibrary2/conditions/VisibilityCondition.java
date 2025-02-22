package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;

import java.util.concurrent.CompletableFuture;


public class VisibilityCondition extends Condition implements TargetCondition {

    private final double maxDistance;

    public VisibilityCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        this.maxDistance = configurationSection.getDouble("maxDistance", 16);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            Location location = entity.getLocation();
            World world = location.getWorld();
            RayTraceResult rayTraceResult = world.rayTraceEntities(location, target.getLocation().subtract(location).toVector(), maxDistance, (other) -> target == other);
            return rayTraceResult.getHitEntity() != null;
        });
    }

}
