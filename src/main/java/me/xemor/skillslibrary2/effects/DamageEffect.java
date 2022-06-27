package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEffect extends Effect implements TargetEffect, EntityEffect {

    private final double damage;
    private EntityDamageEvent.DamageCause damageCause;

    public DamageEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        damage = configurationSection.getDouble("damage", 5);
        try {
            damageCause = EntityDamageEvent.DamageCause.valueOf(configurationSection.getString("cause", "CUSTOM"));
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid damage cause at " + configurationSection.getCurrentPath() + ".cause");
        }
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            livingTarget.damage(damage, entity);
            livingTarget.setLastDamageCause(new EntityDamageEvent(entity, damageCause, damage));
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.damage(damage);
            livingEntity.setLastDamageCause(new EntityDamageEvent(livingEntity, damageCause, damage));
        }
        return false;
    }
}
