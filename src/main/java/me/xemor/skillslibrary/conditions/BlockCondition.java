package me.xemor.skillslibrary.conditions;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public interface BlockCondition {

    boolean isTrue(LivingEntity livingEntity, Block block);

}
