package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ParticleEffect extends Effect implements EntityEffect, LocationEffect, TargetEffect {

    private final Particle particle;
    private final ParticleShape shape;
    private final int count;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;
    private final double extra;
    private final boolean force;

    public ParticleEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        particle = Particle.valueOf(configurationSection.getString("particle", "END_ROD").toUpperCase());
        shape = ParticleShape.valueOf(configurationSection.getString("shape", "SINGLE").toUpperCase());
        count = configurationSection.getInt("count", 1);
        offsetX = configurationSection.getDouble("offsetX", 0.0);
        offsetY = configurationSection.getDouble("offsetY", 0.0);
        offsetZ = configurationSection.getDouble("offsetZ", 0.0);
        extra = configurationSection.getDouble("extra", 0.0);
        force = configurationSection.getBoolean("force");
    }

    @Override
    public boolean useEffect(Entity entity) {
        spawnParticle(entity.getWorld(), entity.getLocation());
        return true;
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        spawnParticle(entity.getWorld(), location, entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        spawnParticle(entity.getWorld(), target.getLocation(), entity.getLocation());
        return false;
    }

    public void spawnParticle(@NotNull World world, @NotNull Location location, Location... locations) {
        switch(shape) {
            case SINGLE -> {
                world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, null, force);
            }
            case LINE -> {
                Location fromLocation = location.clone();
                Location toLocation = locations.length > 0 ? locations[locations.length - 1] : location;
                Vector vector = toLocation.toVector().subtract(location.toVector());

                for (double i = 1; i <= fromLocation.distance(toLocation); i += 0.5) {
                    vector.multiply(i);
                    fromLocation.add(vector);
                    world.spawnParticle(particle, fromLocation, count, offsetX, offsetY, offsetZ, extra, null, force);
                    fromLocation.subtract(vector);
                    vector.normalize();
                }
            }
            case BLOCK_FACE -> {
                double minX = location.getBlockX();
                double minZ = location.getBlockZ();
                double maxX = location.getBlockX() + 1;
                double maxZ = location.getBlockZ() + 1;

                for (double x = minX; x <= maxX; x += 0.05) {
                    for (double z = minZ; z <= maxZ; z += 0.05) {
                        if (x == minX || x >= maxX - 0.05 || z == minZ || z >= maxZ - 0.05) {
                            world.spawnParticle(particle, new Location(world, x, location.getBlockY(), z), count, offsetX, offsetY, offsetZ, extra, null, force);
                        }
                    }
                }
            }
            case BLOCK -> {
                double minX = location.getBlockX();
                double minY = location.getBlockY();
                double minZ = location.getBlockZ();
                double maxX = location.getBlockX() + 1;
                double maxY = location.getBlockY() + 1;
                double maxZ = location.getBlockZ() + 1;

                for (double x = minX; x <= maxX; x += 0.05) {
                    for (double y = minY; y <= maxY; y += 0.05) {
                        for (double z = minZ; z <= maxZ; z += 0.05) {
                            int successes = 0;
                            if (x == minX || x >= maxX - 0.05) successes++;
                            if (y == minY || y >= maxY - 0.05) successes++;
                            if (z == minZ || z >= maxZ - 0.05) successes++;

                            if (successes >= 2) {
                                world.spawnParticle(particle, new Location(world, x, y, z), count, offsetX, offsetY, offsetZ, extra, null, force);
                            }
                        }
                    }
                }
            }
        }
    }

    public enum ParticleShape {
        SINGLE,
        LINE,
        BLOCK_FACE,
        BLOCK
    }
}
