package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

public interface EntityCondition {

    boolean isTrue(Execution execution, Entity entity);

}
