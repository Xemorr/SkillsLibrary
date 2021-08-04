package me.xemor.skillslibrary2.effects;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class SmiteEffect extends Effect implements EntityEffect, TargetEffect {

    private boolean isFake;

    public SmiteEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        isFake = configurationSection.getBoolean("fake", false);
    }

    @Override
    public boolean useEffect(Entity boss) {
        strikeLightning(boss);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        strikeLightning(target);
        return false;
    }

    public void strikeLightning(Entity entity) {
        World world = entity.getWorld();
        if (isFake) {
            world.strikeLightningEffect(entity.getLocation());
        }
        else {
            world.strikeLightning(entity.getLocation());
        }
    }
}
