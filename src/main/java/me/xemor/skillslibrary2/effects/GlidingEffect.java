package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GlidingEffect extends Effect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private boolean glide = true;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setGliding(glide);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof LivingEntity livingTarget) {
            SkillsLibrary.getFoliaHacks().runASAP(target, () -> livingTarget.setGliding(glide));
        }
    }
}
