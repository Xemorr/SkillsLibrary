package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEffect extends Effect implements TargetEffect, EntityEffect {

    @JsonPropertyWithDefault
    private Expression damage = new Expression(5);
    @JsonPropertyWithDefault
    private EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.CUSTOM;
    @JsonPropertyWithDefault
    private boolean shouldTriggerEvents = false;

    @Override
    public void useEffect(Execution exe, Entity entity, Entity target) {
        double calculatedDamage = damage.result(exe, entity, target);
        if (target instanceof LivingEntity livingTarget) {
            if (shouldTriggerEvents) livingTarget.damage(calculatedDamage, entity);
            else livingTarget.damage(calculatedDamage);
            livingTarget.setLastDamageCause(new EntityDamageEvent(entity, damageCause, calculatedDamage));
        }
    }

    @Override
    public void useEffect(Execution exe, Entity entity) {
        double calculatedDamage = damage.result(exe, entity);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(calculatedDamage);
            livingEntity.setLastDamageCause(new EntityDamageEvent(livingEntity, damageCause, calculatedDamage));
        }
    }
}
