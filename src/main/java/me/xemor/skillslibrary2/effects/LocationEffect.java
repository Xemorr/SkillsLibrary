package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface LocationEffect {

    void useEffect(Execution execution, Entity entity, Location location);


}
