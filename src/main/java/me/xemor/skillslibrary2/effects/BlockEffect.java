package me.xemor.skillslibrary2.effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface BlockEffect {

    boolean useEffect(Entity entity, Block block);

}
