package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class VelocityEffect extends Effect implements EntityEffect, TargetEffect {

    private double velocity;

    public VelocityEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(Entity livingEntity) {
        livingEntity.setVelocity(livingEntity.getVelocity().setY(velocity));
        return false;
    }

    @Override
    public boolean useEffect(Entity livingEntity, Entity target) {
        target.setVelocity(target.getVelocity().setY(velocity));
        return false;
    }

}
