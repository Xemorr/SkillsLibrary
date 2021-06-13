package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

/**
 * An effect that picks up the target entity and places it on the user's head.
 */
public class Pickup extends Effect implements TargetEffect {

    public Pickup(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(LivingEntity boss, Entity target) {
        if (!boss.getPassengers().contains(target)) boss.addPassenger(target);
        return false;
    }
}
