package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public class TargetEntityEffect extends Effect implements TargetEffect {

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (entity instanceof Mob mob && target instanceof LivingEntity livingTarget) {
            mob.setTarget(livingTarget);
        }
    }

}
