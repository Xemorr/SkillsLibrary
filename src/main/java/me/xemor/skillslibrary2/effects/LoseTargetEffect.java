package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

public class LoseTargetEffect extends Effect implements EntityEffect, TargetEffect {

    @Override
    public void useEffect(Execution execution, Entity entity) {
        SkillsLibrary.getFoliaHacks().runASAP(entity, () -> {
            if (entity instanceof Mob mob) {
                mob.setTarget(null);
            }
        });
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity other) {
        useEffect(execution, other);
    }
}
