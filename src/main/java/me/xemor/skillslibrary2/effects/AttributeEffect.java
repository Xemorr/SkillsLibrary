package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class AttributeEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    @JsonPropertyWithDefault
    private Attribute attribute = null;

    @Override
    public void useEffect(Execution execution, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            applyAttributes(execution, livingEntity);
        }
    }

    public void applyAttributes(Execution execution, LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        attributeInstance.setBaseValue(changeValue(execution, attributeInstance.getBaseValue(), entity));
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        if (target instanceof LivingEntity targetLivingEntity) {
            SkillsLibrary.getFoliaHacks().runASAP(targetLivingEntity, () -> {
                AttributeInstance attributeInstance = targetLivingEntity.getAttribute(attribute);
                attributeInstance.setBaseValue(changeValue(execution, attributeInstance.getBaseValue(), entity, targetLivingEntity));
            });
        }
    }
}
