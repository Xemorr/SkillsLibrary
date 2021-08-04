package me.xemor.skillslibrary2.conditions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

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
    public boolean isTrue(Entity entity, Block block) {
        return biomes.contains(block.getBiome());
    }

    @Override
    public boolean isTrue(Entity entity) {
        return biomes.contains(getBiome(entity.getLocation()));
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return biomes.contains(getBiome(target.getLocation()));
    }

    public Biome getBiome(Location location) {
        return location.getBlock().getBiome();
    }

}
