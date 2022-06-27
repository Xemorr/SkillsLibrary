package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GlidingEffect extends Effect implements EntityEffect, TargetEffect {

    private final boolean glide;

    public GlidingEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        glide = configurationSection.getBoolean("glide", true);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setGliding(glide);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            livingTarget.setGliding(glide);
        }
        return false;
    }
}
