package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VelocityEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    private String component;

    public VelocityEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        if (!configurationSection.contains("value")) super.valueExpr = configurationSection.getString("velocity", "1.0");
        // default is Y to maintain backwards compatibility unfortunately
        // would be better for it to be all
        component = configurationSection.getString("component", "Y");
        if ("ALL".equalsIgnoreCase(component)) {
            component = "XYZ";
        }
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity) {
        Vector velocity = livingEntity.getVelocity();
        if (component.contains("X") || component.contains("x")) {
            velocity = velocity.setX(changeValue(execution, velocity.getX()));
        }
        if (component.contains("Y") || component.contains("y")) {
            velocity = velocity.setY(changeValue(execution, velocity.getY()));
        }
        if (component.contains("Z") || component.contains("z")) {
            velocity = velocity.setZ(changeValue(execution, velocity.getZ()));
        }
        livingEntity.setVelocity(velocity);
    }

    @Override
    public void useEffect(Execution execution, Entity livingEntity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
    }

}
