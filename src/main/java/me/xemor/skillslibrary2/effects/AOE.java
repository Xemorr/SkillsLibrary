package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Collection;

public class AOE extends WrapperEffect implements EntityEffect, TargetEffect, ComplexLocationEffect {

    @JsonPropertyWithDefault
    private Expression radius = new Expression(5);

    @Override
    public void useEffect(Execution execution, Entity skillEntity, Location location) {
        double finalRadius = radius.result(execution, skillEntity);
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, finalRadius, finalRadius, finalRadius);
        for (Entity entity : entities) {
            if (entity != skillEntity) {
                handleEffects(execution, skillEntity, entity);
            }
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        useEffect(execution, entity, entity.getLocation());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        useEffect(execution, entity, target.getLocation());
    }
}
