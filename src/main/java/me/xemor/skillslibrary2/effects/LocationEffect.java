package me.xemor.skillslibrary2.effects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface LocationEffect {

    boolean useEffect(Entity entity, Location location);

}
