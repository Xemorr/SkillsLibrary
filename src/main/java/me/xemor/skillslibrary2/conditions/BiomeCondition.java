package me.xemor.skillslibrary2.conditions;

import me.xemor.configurationdata.comparison.RegistrySetData;
import me.xemor.configurationdata.comparison.SetData;
import me.xemor.skillslibrary2.Skill;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Location;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BiomeCondition extends Condition implements EntityCondition, TargetCondition, LocationCondition {

    private final RegistrySetData<Biome> biomes;

    public BiomeCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        biomes = new RegistrySetData<>(Registry.BIOME::match, "biomes", configurationSection);
    }

    @Override
    public boolean isTrue(Entity entity, Location location) {
        return biomes.inSet(location.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Override
    public boolean isTrue(Entity entity) {
        return biomes.inSet(getBiome(entity.getLocation()));
    }

    @Override
    public boolean isTrue(Entity entity, Entity target) {
        return biomes.inSet(getBiome(target.getLocation()));
    }

    public Biome getBiome(Location location) {
        return location.getBlock().getBiome();
    }

}
