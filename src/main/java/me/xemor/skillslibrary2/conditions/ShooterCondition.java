package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

public class ShooterCondition extends Condition implements TargetCondition {
    public ShooterCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        if (target instanceof Projectile projectile) {
            return entity.equals(projectile.getShooter());
        }
        return false;
    }
}
