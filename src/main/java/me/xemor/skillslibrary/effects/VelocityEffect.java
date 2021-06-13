package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class VelocityEffect extends Effect implements EntityEffect, TargetEffect {

    private double velocity;

    public VelocityEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity) {
        livingEntity.setVelocity(livingEntity.getVelocity().setY(velocity));
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        entity.setVelocity(entity.getVelocity().setY(velocity));
        return false;
    }

}
