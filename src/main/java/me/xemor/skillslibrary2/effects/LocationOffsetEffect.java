package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class LocationOffsetEffect extends WrapperEffect implements EntityEffect, ComplexLocationEffect, TargetEffect {

    @JsonPropertyWithDefault
    private Vector offset = new Vector(0, 0, 0);

    @Override
    public void useEffect(Execution execution, Entity entity, Location location) {
        handleEffects(execution, entity, location.add(offset));
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> useEffect(execution, entity, entity.getLocation()));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, entity, target.getLocation()));
    }

}
