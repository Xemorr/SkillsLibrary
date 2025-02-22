package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public class ShooterEffect extends WrapperEffect implements TargetEffect {

    private final boolean onlyProjectiles;

    public ShooterEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        onlyProjectiles = configurationSection.getBoolean("onlyProjectiles", true);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            handleEffects(execution, entity);
            handleEffects(execution, entity, shooter);
        }
        else if (!onlyProjectiles) {
            handleEffects(execution, entity);
            handleEffects(execution, entity, target);
        }
    }
}
