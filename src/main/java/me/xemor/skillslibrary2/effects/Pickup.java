package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

/**
 * An effect that picks up the target entity and places it on the user's head.
 */
public class Pickup extends Effect implements TargetEffect {

    public Pickup(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        if (!entity.getPassengers().contains(target)) entity.addPassenger(target);
        return false;
    }
}
