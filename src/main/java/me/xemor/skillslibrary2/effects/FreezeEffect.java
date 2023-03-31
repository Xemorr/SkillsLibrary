package me.xemor.skillslibrary2.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class FreezeEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    public FreezeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setFreezeTicks((int) changeValue(livingEntity.getFreezeTicks()));
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        return useEffect(target);
    }
}
