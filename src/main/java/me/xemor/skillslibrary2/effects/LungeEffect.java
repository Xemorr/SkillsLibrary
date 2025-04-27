package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class LungeEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private Expression horizontalVelocity = new Expression(0);
    @JsonPropertyWithDefault
    private Expression verticalVelocity = new Expression(0);
    @JsonPropertyWithDefault
    private boolean overwrite = true;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            SkillsLibrary.getFoliaHacks().runASAP(livingEntity, () -> {
                setVelocity(execution, livingEntity);
            });
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof LivingEntity livingTarget) {
            SkillsLibrary.getFoliaHacks().runASAP(livingTarget, () -> {
                setVelocity(execution, livingTarget);
            });
        }
    }

    public void setVelocity(Execution execution, LivingEntity entity) {
        Vector direction;
        if (entity instanceof Player)
            direction = entity.getEyeLocation().getDirection();
        else {
            direction = entity.getLocation().getDirection();
        }
        String entityMode = getMode() == Mode.SELF ? "self" : "other";
        double resultHorizontalVelocity = horizontalVelocity.result(execution, Map.of(entityMode, entity));
        double resultVerticalVelocity = verticalVelocity.result(execution, Map.of(entityMode, entity));
        double x = direction.getX() * resultHorizontalVelocity;
        double z = direction.getZ() * resultHorizontalVelocity;
        double y = direction.getY() * resultVerticalVelocity;
        if (overwrite) entity.setVelocity(new Vector(x, y, z));
        else entity.setVelocity(entity.getVelocity().add(new Vector(x, y, z)));
    }
}
