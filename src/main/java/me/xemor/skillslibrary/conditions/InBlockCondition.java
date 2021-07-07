package me.xemor.skillslibrary.conditions;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class InBlockCondition extends Condition implements EntityCondition, TargetCondition {

    private final List<Material> blocks;

    public InBlockCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        blocks = configurationSection.getStringList("blocks").stream().map(String::toUpperCase).map(Material::valueOf).collect(Collectors.toList());
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return inBlock(boss);
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return inBlock(target);
    }

    public boolean inBlock(Entity entity) {
        return blocks.contains(entity.getLocation().getBlock().getType());
    }
}
