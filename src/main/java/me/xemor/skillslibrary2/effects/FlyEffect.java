package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class FlyEffect extends Effect implements EntityEffect, TargetEffect {

    private final boolean fly;

    public FlyEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        fly = configurationSection.getBoolean("fly", true);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof Player) {
            ((Player) entity).setFlying(fly);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        return useEffect(target);
    }
}
