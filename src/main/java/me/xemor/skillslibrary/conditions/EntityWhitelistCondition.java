package me.xemor.skillslibrary.conditions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class EntityWhitelistCondition extends Condition implements TargetCondition {

    private final boolean whitelist;
    private final Set<EntityType> entities;

    public EntityWhitelistCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        whitelist = configurationSection.getBoolean("whitelist", true);
        entities = configurationSection.getStringList("entities").stream().map(String::toUpperCase).map(EntityType::valueOf).collect(Collectors.toSet());
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        boolean contained = entities.contains(target.getType());
        return whitelist == contained;
    }

}
