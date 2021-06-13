package me.xemor.skillslibrary.conditions;

import org.bukkit.entity.LivingEntity;

public interface EntityCondition {

    boolean isTrue(LivingEntity boss);

}
