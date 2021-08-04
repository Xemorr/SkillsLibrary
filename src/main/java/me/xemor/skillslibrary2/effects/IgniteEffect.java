package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class IgniteEffect extends Effect implements EntityEffect, TargetEffect {

    private final int fireTicks;

    public IgniteEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        fireTicks = configurationSection.getInt("fireTicks");
    }

    @Override
    public boolean useEffect(Entity entity) {
        entity.setFireTicks(fireTicks);
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        target.setFireTicks(fireTicks);
        return false;
    }
}
