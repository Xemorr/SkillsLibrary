package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private EntityData entity = new EntityData().setType(EntityType.FIREBALL);
    @JsonPropertyWithDefault
    private Expression velocity = new Expression(1.0);

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(
                entity, () -> {
                    Vector entityLocation;
                    if (entity instanceof Player player) {
                        entityLocation = player.getEyeLocation().toVector();
                    } else {
                        entityLocation = entity.getLocation().toVector();
                    }
                    SkillsLibrary.getFoliaHacks().runASAP(target, () -> {
                        Vector direction = target.getLocation().subtract(entityLocation).toVector().normalize();
                        Vector spawnLocation = entityLocation.clone().add(direction);
                        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
                            Entity projectile = this.entity.spawnEntity(new Location(entity.getWorld(), spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ()));
                            projectile.setVelocity(direction.multiply(velocity.result(execution, entity, target)));
                        });
                    });
                }
        );
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        Location spawnLocation;
        Vector direction;
        if (entity instanceof Player player) {
            direction = player.getEyeLocation().getDirection();
            spawnLocation = player.getEyeLocation().clone().add(direction);
        } else {
            direction = entity.getLocation().getDirection();
            spawnLocation = entity.getLocation().clone().add(direction);
        }
        Entity projectile = this.entity.spawnEntity(spawnLocation);
        projectile.setVelocity(direction.multiply(velocity.result(execution, entity, entity)));
    }
}
