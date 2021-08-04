package me.xemor.skillslibrary2.conditions;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface BlockCondition {

    boolean isTrue(Entity entity, Block block);

}
