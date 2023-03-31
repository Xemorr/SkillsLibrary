package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchEffect extends Effect implements EntityEffect, TargetEffect {

    private final EntityType entityType;
    private final double velocity;

    public LaunchEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.entityType = EntityType.valueOf(configurationSection.getString("entity", "FIREBALL"));
        this.velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        World world = entity.getWorld();
        Location entityLocation;
        if (entity instanceof Player player) {
            entityLocation = player.getEyeLocation();
        } else {
            entityLocation = entity.getLocation();
        }
        Vector direction = target.getLocation().subtract(entityLocation).toVector().normalize();
        Location spawnLocation = entityLocation.clone().add(direction);
        Entity projectile = world.spawnEntity(spawnLocation, entityType);
        projectile.setVelocity(direction.multiply(velocity));
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        World world = entity.getWorld();
        Location spawnLocation;
        Vector direction;
        if (entity instanceof Player player) {
            direction = player.getEyeLocation().getDirection();
            spawnLocation = player.getEyeLocation().clone().add(direction);
        } else {
            direction = entity.getLocation().getDirection();
            spawnLocation = entity.getLocation().clone().add(direction);
        }
        Entity projectile = world.spawnEntity(spawnLocation, entityType);
        projectile.setVelocity(direction.multiply(velocity));
        return false;
    }
}
