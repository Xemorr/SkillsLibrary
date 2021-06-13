package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GlidingEffect extends Effect implements EntityEffect, TargetEffect {

    private final boolean glide;

    public GlidingEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        glide = configurationSection.getBoolean("glide");
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        entity.setGliding(glide);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) entity;
            livingTarget.setGliding(glide);
        }
        return false;
    }
}
