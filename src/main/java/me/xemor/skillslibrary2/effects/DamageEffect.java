package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DamageEffect extends Effect implements TargetEffect, EntityEffect {

    double damage;

    public DamageEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        damage = configurationSection.getDouble("damage", 5);
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            livingTarget.damage(damage, entity);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.damage(damage);
        }
        return false;
    }
}
