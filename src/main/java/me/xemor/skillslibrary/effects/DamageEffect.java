package me.xemor.skillslibrary.effects;

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
    public boolean useEffect(LivingEntity livingEntity, Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            livingTarget.damage(damage, livingEntity);
        }
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        entity.damage(damage);
        return false;
    }
}
