package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;

/**
 * An effect that picks up the target entity and places it on the user's head.
 */
public class Pickup extends Effect implements TargetEffect {

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            if (!entity.getPassengers().contains(target)) {
                entity.addPassenger(target);
            }
        });
    }
}
