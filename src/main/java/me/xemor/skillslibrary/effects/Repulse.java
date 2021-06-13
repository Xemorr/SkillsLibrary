package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class Repulse extends Effect implements TargetEffect {

    private final double velocity;

    public Repulse(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(LivingEntity bossEntity, Entity target) {
        repulse(bossEntity, target);
        return false;
    }

    public void repulse(LivingEntity boss, Entity target) {
        Vector repulseVelocity = target.getLocation().subtract(boss.getLocation()).toVector().normalize().multiply(velocity);
        if (Double.isNaN(repulseVelocity.lengthSquared())) {
            return;
        }
        target.setVelocity(repulseVelocity);
    }
}
