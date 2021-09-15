package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class ItemStackWrapperEffect extends WrapperEffect implements EntityEffect, TargetEffect {

    public ItemStackWrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        return false;
    }
}
