package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

import java.util.concurrent.CompletableFuture;

public class ShooterCondition extends Condition implements TargetCondition {
    public ShooterCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        if (target instanceof Projectile projectile) {
            return SkillsLibrary.getFoliaHacks().runASAP(entity, () -> entity.equals(projectile.getShooter()));
        }
        return CompletableFuture.completedFuture(false);
    }
}
