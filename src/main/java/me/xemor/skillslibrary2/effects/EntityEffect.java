package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

public interface EntityEffect {

    void useEffect(Execution execution, Entity entity);

}
