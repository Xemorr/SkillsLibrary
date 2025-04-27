package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class RaytraceEffect extends WrapperEffect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private double maxDistance = 10;
    @JsonPropertyWithDefault
    private FluidCollisionMode collisionMode = FluidCollisionMode.NEVER;
    @JsonPropertyWithDefault
    private boolean ignorePassables = true;
    @JsonPropertyWithDefault
    private boolean alwaysHit = true;
    @JsonPropertyWithDefault
    @JsonAlias("raysize")
    private double raySize = 1.0;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        World world = entity.getWorld();
        Location location;
        if (entity instanceof Player) {
            location = ((Player) entity).getEyeLocation();
        }
        else {
            location = entity.getLocation();
        }
        Vector direction = location.getDirection();
        RayTraceResult rayTraceResult = world.rayTrace(location,
                location.getDirection(),
                maxDistance,
                FluidCollisionMode.NEVER,
                ignorePassables,
                raySize,
                (other) -> other != entity);
        Object result;
        if (rayTraceResult == null && alwaysHit) {
            result = location.add(direction.multiply(maxDistance));
        } else if (rayTraceResult == null) {
            return;
        } else if (rayTraceResult.getHitEntity() != null) {
            result = rayTraceResult.getHitEntity();
        } else {
            Vector position = rayTraceResult.getHitPosition();
            result = new Location(world, position.getX(), position.getY(), position.getZ(), location.getYaw(), location.getPitch());
        }
        handleEffects(execution, entity, result);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        useEffect(execution, target);
    }

}
