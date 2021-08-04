package me.xemor.skillslibrary2.conditions;

import org.bukkit.entity.Entity;

public interface TargetCondition {

    boolean isTrue(Entity entity, Entity target);

}
