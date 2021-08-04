package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class ParticleEffect extends Effect implements EntityEffect, TargetEffect {
    public ParticleEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        return false;
    }
}
