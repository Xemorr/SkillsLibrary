package me.xemor.skillslibrary.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface TargetCondition {

    boolean isTrue(LivingEntity skillEntity, Entity target);

}
