package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;


public class VisibilityCondition extends Condition implements TargetCondition {

    private double maxDistance;

    public VisibilityCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        this.maxDistance = configurationSection.getDouble("maxDistance", 16);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        Location location = entity.getLocation();
        World world = location.getWorld();
        RayTraceResult rayTraceResult = world.rayTraceEntities(location, target.getLocation().subtract(location).toVector(), maxDistance, (other) -> target == other);
        return rayTraceResult.getHitEntity() != null;
    }

}
