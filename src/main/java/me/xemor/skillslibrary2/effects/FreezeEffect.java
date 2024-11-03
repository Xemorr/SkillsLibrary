package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class FreezeEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    public FreezeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setFreezeTicks((int) changeValue(execution, livingEntity.getFreezeTicks()));
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> useEffect(execution, target));
    }
}
