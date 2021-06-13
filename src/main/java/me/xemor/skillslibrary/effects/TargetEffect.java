package me.xemor.skillslibrary.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface TargetEffect {

    boolean useEffect(LivingEntity livingEntity, Entity entity);

}
