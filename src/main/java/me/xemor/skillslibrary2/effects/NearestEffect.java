package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class NearestEffect extends WrapperEffect implements EntityEffect, TargetEffect, LocationEffect {

    private final double radius;

    public NearestEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        LivingEntity nearest = getNearest(entity, location);
        return handleEffects(entity, nearest);
    }

    @Override
    public boolean useEffect(Entity entity) {
        LivingEntity nearest = getNearest(entity, entity.getLocation());
        return handleEffects(entity, nearest);
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        LivingEntity nearest = getNearest(livingEntity, target.getLocation());
        if (nearest == null) return false;
        return handleEffects(livingEntity, nearest);
    }

    @Nullable
    public LivingEntity getNearest(Entity livingEntity, Location location) {
        World world = location.getWorld();
        Collection<Entity> entities = world.getNearbyEntities(location, radius, radius, radius);
        entities.removeIf((entity -> !(entity instanceof LivingEntity)));
        entities.removeIf((entity -> !getConditions().areConditionsTrue(livingEntity, entity)));
        if (entities.size() == 0) {
            return null;
        }
        return (LivingEntity) Collections.min(entities, Comparator.comparingDouble(entity -> entity.getLocation().distanceSquared(location)));
    }

}
