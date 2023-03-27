package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RepulseEffect extends Effect implements TargetEffect {

    private final double velocity;

    public RepulseEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        repulse(entity, target);
        return false;
    }

    public void repulse(Entity boss, Entity target) {
        Vector repulseVelocity = target.getLocation().subtract(boss.getLocation()).toVector().normalize().multiply(velocity);
        if (Double.isNaN(repulseVelocity.lengthSquared())) {
            return;
        }
        target.setVelocity(repulseVelocity);
    }
}
