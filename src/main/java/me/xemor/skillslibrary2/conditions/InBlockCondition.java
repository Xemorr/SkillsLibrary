package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class InBlockCondition extends Condition implements EntityCondition, TargetCondition {

    private final List<Material> blocks;

    public InBlockCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        blocks = configurationSection.getStringList("blocks").stream().map(String::toUpperCase).map(Material::valueOf).collect(Collectors.toList());
    }

    @Override
    public boolean isTrue(Execution execution, Entity boss) {
        return inBlock(boss);
    }

    @Override
    public CompletableFuture<Boolean> isTrue(Execution execution, Entity entity, Entity target) {
        return SkillsLibrary.getFoliaHacks().runASAP(target, () -> inBlock(target));
    }

    public boolean inBlock(Entity entity) {
        return blocks.contains(entity.getLocation().getBlock().getType());
    }
}
