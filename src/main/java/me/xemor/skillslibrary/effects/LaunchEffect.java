package me.xemor.skillslibrary.effects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LaunchEffect extends Effect implements TargetEffect {

    private final EntityType entityType;
    private final double velocity;

    public LaunchEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.entityType = EntityType.valueOf(configurationSection.getString("type", "FIREBALL"));
        this.velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(LivingEntity boss, Entity target) {
        World world = boss.getWorld();
        Vector direction = target.getLocation().subtract(boss.getLocation()).toVector().normalize();
        Location spawnLocation = boss.getLocation().clone().add(direction);
        Entity entity = world.spawnEntity(spawnLocation, entityType);
        entity.setVelocity(direction.multiply(velocity));
        return false;
    }
}
