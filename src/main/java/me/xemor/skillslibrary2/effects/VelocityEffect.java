package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VelocityEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private String component = "Y";

    @Override
    public void useEffect(Execution execution, Entity livingEntity) {
        if ("ALL".equalsIgnoreCase(component)) {
            component = "XYZ";
        }
        component = component.toUpperCase();
        Vector velocity = livingEntity.getVelocity();
        if (component.contains("X")) {
            velocity = velocity.setX(changeValue(execution, velocity.getX()));
        }
        if (component.contains("Y")) {
            velocity = velocity.setY(changeValue(execution, velocity.getY()));
        }
        if (component.contains("Z")) {
            velocity = velocity.setZ(changeValue(execution, velocity.getZ()));
        }
        livingEntity.setVelocity(velocity);
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
    }

}
