package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAlias;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.entity.EntityData;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class ArrowEffect extends Effect implements TargetEffect {

    @JsonPropertyWithDefault
    private Expression velocity = new Expression(1.0);
    @JsonPropertyWithDefault
    private Expression damage = new Expression(4);
    @JsonPropertyWithDefault
    @JsonAlias("entity")
    private EntityData entityData = new EntityData().setType(EntityType.ARROW);
    @JsonPropertyWithDefault
    private Expression fireTicks = new Expression(0);
    private static final double log099 = Math.log(0.99);

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        Location location = target.getLocation();
        if (entity instanceof LivingEntity livingEntity) {
            Location startPoint = livingEntity.getEyeLocation();
            double yDifference = location.getY() - startPoint.getY();
            double xDifference = location.getX() - startPoint.getX();
            double zDifference = location.getZ() - startPoint.getZ();
            double length = Math.sqrt(yDifference * yDifference + xDifference * xDifference + zDifference * zDifference);
            if (length < 0.1) return;
            int time = (int) Math.round(length / velocity.result(execution, entity, target));
            double initialYVelocity = solveForInitialVerticalVelocity(yDifference, time);
            double initialXVelocity = solveForInitialHorizontalVelocity(xDifference, time);
            double initialZVelocity = solveForInitialHorizontalVelocity(zDifference, time);
            Vector vector = new Vector(initialXVelocity, initialYVelocity, initialZVelocity);
            Entity spawnedEntity = entityData.spawnEntity(startPoint);
            if (spawnedEntity instanceof Arrow arrow) {
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.setDamage(damage.result(execution, entity, target));
            }
            if (spawnedEntity instanceof Projectile projectile) {
                projectile.setShooter(livingEntity);
            }
            spawnedEntity.setFireTicks((int) Math.round(fireTicks.result(execution, entity, target)));
            startPoint = startPoint.add(vector.clone().normalize());
            spawnedEntity.teleport(startPoint);
            spawnedEntity.setVelocity(vector);
        }
    }

    private double solveForInitialVerticalVelocity(double displacement, int time) {
        double zero99ToT = Math.pow(0.99, time);
        double numerator = 5 * zero99ToT - log099 * displacement - 5 * log099 * time - 5;
        double denominator = 1 - zero99ToT;
        return numerator / denominator;
    }


    private double solveForInitialHorizontalVelocity(double displacement, int time) {
        double zero99ToT = Math.pow(0.99, time);
        double numerator = -log099 * displacement;
        double denominator = 1 - zero99ToT;
        return numerator / denominator;
    }


    private double calculateHorizontalVelocity(int ticksInFuture, double velocity) {
        return velocity * Math.pow(0.99, ticksInFuture);
    }

}
