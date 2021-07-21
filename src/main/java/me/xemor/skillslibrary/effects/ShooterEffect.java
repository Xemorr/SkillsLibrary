package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public class ShooterEffect extends WrapperEffect implements TargetEffect {

    private final boolean onlyProjectiles;

    public ShooterEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        onlyProjectiles = configurationSection.getBoolean("onlyProjectiles", true);
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        if (entity instanceof Projectile) {
            Projectile projectile = (Projectile) entity;
            ProjectileSource shooter = projectile.getShooter();
            handleEffects(livingEntity, shooter);
        }
        else if (!onlyProjectiles) {
            handleEffects(livingEntity, entity);
        }
        return false;
    }
}
