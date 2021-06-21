package me.xemor.skillslibrary.conditions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BiomeCondition extends Condition implements EntityCondition, TargetCondition, BlockCondition {

    Set<Biome> biomes;

    public BiomeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        biomes = configurationSection.getStringList("biomes").stream()
                .map(String::toUpperCase).map(Biome::valueOf).collect(Collectors.toSet());
    }

    @Override
    public boolean isTrue(LivingEntity livingEntity, Block block) {
        return biomes.contains(block.getBiome());
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return biomes.contains(getBiome(boss.getLocation()));
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return biomes.contains(getBiome(target.getLocation()));
    }

    public Biome getBiome(Location location) {
        World world = location.getWorld();
        return world.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

}
