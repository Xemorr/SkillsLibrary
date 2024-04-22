package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class ArrowEffect extends Effect implements TargetEffect {

    private final double velocity;
    private final int damage;
    private final EntityData entityData;
    private final int fireTicks;
    private static final double log099 = Math.log(0.99);

    public ArrowEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        velocity = configurationSection.getDouble("velocity", 1.0);
        damage = configurationSection.getInt("damage", 4);

        if (configurationSection.isString("entity")) {
            this.entityData = EntityData.create(EntityType.valueOf(configurationSection.getString("entity")));
        } else {
            ConfigurationSection entitySection = configurationSection.getConfigurationSection("entity");
            this.entityData = entitySection != null ? EntityData.create(entitySection, EntityType.ARROW) : EntityData.create(EntityType.ARROW);
        }

        fireTicks = configurationSection.getInt("fireTicks", 0);
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        Location location = target.getLocation();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            Location startPoint = livingEntity.getEyeLocation();
            World world = entity.getWorld();
            double yDifference = location.getY() - startPoint.getY();
            double xDifference = location.getX() - startPoint.getX();
            double zDifference = location.getZ() - startPoint.getZ();
            double length = Math.sqrt(yDifference * yDifference + xDifference * xDifference + zDifference * zDifference);
            if (length < 0.1) return false;
            int time = (int) Math.round(length / velocity);
            double initialYVelocity = solveForInitialVerticalVelocity(yDifference, time);
            double initialXVelocity = solveForInitialHorizontalVelocity(xDifference, time);
            double initialZVelocity = solveForInitialHorizontalVelocity(zDifference, time);
            Vector vector = new Vector(initialXVelocity, initialYVelocity, initialZVelocity);
            Entity spawnedEntity = entityData.spawnEntity(world, startPoint);
            if (spawnedEntity instanceof Arrow arrow) {
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.setDamage(damage);
            }
            if (spawnedEntity instanceof Projectile projectile) {
                projectile.setShooter(livingEntity);
            }
            spawnedEntity.setFireTicks(fireTicks);
            startPoint = startPoint.add(vector.clone().normalize());
            spawnedEntity.teleport(startPoint);
            spawnedEntity.setVelocity(vector);
        }
        return false;
    }

    private double solveForInitialVerticalVelocity(double displacement, int time) {
        double zero99ToT = Math.pow(0.99, time);
        double numerator = 5 * zero99ToT - log099 * displacement - 5 * log099 * time - 5;
        double denominator = 1 - zero99ToT;
        return numerator / denominator;
    }


    private double solveForInitialHorizontalVelocity(double displacement, int time) {
        double zero99ToT = Math.pow(0.99, time);
        double numerator = -log099 * displacement;
        double denominator = 1 - zero99ToT;
        return numerator / denominator;
    }


    private double calculateHorizontalVelocity(int ticksInFuture, double velocity) {
        return velocity * Math.pow(0.99, ticksInFuture);
    }

}
