package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ProjectileEffect extends Effect implements EntityEffect, TargetEffect {

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
    public boolean useEffect(LivingEntity entity) {
        Vector velocity = entity.getEyeLocation().getDirection().multiply(this.velocity);
        Entity projectile = this.projectile.createEntity(entity.getWorld(), entity.getEyeLocation().add(velocity));
        projectile.setVelocity(velocity);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        Vector velocity = entity.getLocation().subtract(livingEntity.getLocation()).toVector().normalize().multiply(this.velocity);
        Entity projectile = this.projectile.createEntity(entity.getWorld(), livingEntity.getEyeLocation().add(velocity));
        projectile.setVelocity(velocity);
        return false;
    }
}
