package me.xemor.skillslibrary.conditions;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class WorldCondition extends Condition implements EntityCondition, TargetCondition, BlockCondition {

    List<String> worlds;

    public WorldCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        worlds = configurationSection.getStringList("worlds");
    }

    @Override
    public boolean isTrue(LivingEntity livingEntity, Block block) {
        return worlds.contains(block.getWorld().getName());
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return worlds.contains(boss.getWorld().getName());
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return worlds.contains(target.getWorld().getName());
    }
}
