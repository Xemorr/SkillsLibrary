package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class LocationCubeEffect extends WrapperEffect implements EntityEffect, ComplexLocationEffect, TargetEffect {

    @JsonPropertyWithDefault
    private final int verticalRadius = 0;
    @JsonPropertyWithDefault
    private final int horizontalRadius = 0;
    @JsonPropertyWithDefault
    private final Vector offset = new Vector(0, 0, 0);

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        Location cubeCentre = location.add(offset);
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int j = -horizontalRadius; j <= horizontalRadius; j++) {
                for (int k = -verticalRadius; k <= verticalRadius; k++) {
                    handleEffects(execution, entity, cubeCentre.clone().add(i, k, j));
                }
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
