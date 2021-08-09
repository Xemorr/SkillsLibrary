package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ProjectileEffect extends Effect implements EntityEffect, TargetEffect, LocationEffect {

    private final EntityData projectile;
    private final double velocity;

    public ProjectileEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection entity = configurationSection.getConfigurationSection("entity");
        if (entity == null) {
            SkillsLibrary.getInstance().getLogger().severe("There hasn't been an entity specified to be fired for Projectile effect at " + configurationSection.getCurrentPath() + ".entity");
        }
        projectile = new EntityData(entity);
        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            Vector velocity = livingEntity.getEyeLocation().getDirection().multiply(this.velocity);
            Entity projectile = this.projectile.createEntity(entity.getWorld(), livingEntity.getEyeLocation().add(velocity));
            projectile.setVelocity(velocity);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, target.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        Vector velocity = location.subtract(entity.getLocation()).toVector().normalize().multiply(this.velocity);
        Entity projectileEntity = projectile.createEntity(location.getWorld(), entity.getLocation().add(velocity));
        projectileEntity.setVelocity(velocity);
        return false;
    }
}
