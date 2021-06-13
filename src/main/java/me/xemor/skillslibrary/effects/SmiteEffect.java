package me.xemor.skillslibrary.effects;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class SmiteEffect extends Effect implements EntityEffect, TargetEffect {

    private boolean isFake;

    public SmiteEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        isFake = configurationSection.getBoolean("fake", false);
    }

    @Override
    public boolean useEffect(LivingEntity boss) {
        strikeLightning(boss);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity boss, Entity target) {
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
