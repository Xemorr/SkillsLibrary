package me.xemor.skillslibrary.effects;

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
import java.util.List;

public class NearestEffect extends WrapperEffect implements EntityEffect, TargetEffect, BlockEffect {

    private double radius;

    public NearestEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        radius = configurationSection.getDouble("radius", 5);
    }

    @Override
    public boolean useEffect(LivingEntity entity, Block block) {
        LivingEntity nearest = getNearest(entity, block.getLocation());
        return handleEffects(entity, nearest);
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        LivingEntity nearest = getNearest(entity, entity.getLocation());
        return handleEffects(entity, nearest);
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        LivingEntity nearest = getNearest(livingEntity, entity.getLocation());
        return handleEffects(livingEntity, nearest);
    }

    @Nullable
    public LivingEntity getNearest(LivingEntity livingEntity, Location location) {
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
