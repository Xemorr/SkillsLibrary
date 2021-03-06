package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class RemoveEntityEffect extends Effect implements EntityEffect, TargetEffect {

    public RemoveEntityEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        entity.remove();
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        target.remove();
        return false;
    }
}
