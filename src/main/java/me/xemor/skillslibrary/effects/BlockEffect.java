package me.xemor.skillslibrary.effects;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public interface BlockEffect {

    boolean useEffect(LivingEntity entity, Block block);

}
