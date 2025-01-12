package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnEffect extends Effect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    private final EntityData entityData;

    public SpawnEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.entityData = EntityData.create(configurationSection, "entity", EntityType.ZOMBIE);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        useEffect(execution, entity, entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        entityData.spawnEntity(location);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        useEffect(execution, entity, target.getLocation());
    }
}
