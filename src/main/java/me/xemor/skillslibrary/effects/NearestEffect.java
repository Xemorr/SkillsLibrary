package me.xemor.skillslibrary.effects;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class NearestEffect extends WrapperEffect implements EntityEffect, TargetEffect, BlockEffect {
    public NearestEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public boolean useEffect(LivingEntity entity, Block block) {

    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        return false;
    }
}
