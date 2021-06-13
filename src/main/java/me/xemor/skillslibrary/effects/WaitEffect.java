package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitEffect extends WrapperEffect implements EntityEffect, TargetEffect {

    long ticksDelay;

    public WaitEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ticksDelay = Math.round(configurationSection.getDouble("delay", 1) * 20);
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Effect effect : getNextEffects()) {
                    if (effect instanceof EntityEffect) {
                        EntityEffect entityEffect = (EntityEffect) effect;
                        entityEffect.useEffect(livingEntity);
                    }
                }
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay);
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Effect effect : getNextEffects()) {
                    if (effect instanceof TargetEffect) {
                        TargetEffect targetEffect = (TargetEffect) effect;
                        targetEffect.useEffect(livingEntity, entity);
                    }
                }
            }
        }.runTaskLater(SkillsLibrary.getInstance(), ticksDelay);
        return false;
    }
}
