package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface LocationCondition {

    boolean isTrue(Entity entity, Location location);

}
