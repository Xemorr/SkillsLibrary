package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnEffect extends Effect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    @JsonPropertyWithDefault
    private EntityData entity = new EntityData().setType(EntityType.ZOMBIE);

    @Override
    public void useEffect(Execution execution, Entity entity) {
        useEffect(execution, entity, entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        this.entity.spawnEntity(location);
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        useEffect(execution, entity, target.getLocation());
    }
}
