package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.ConfigurationData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class AttributeEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    private Attribute attribute = null;

    public AttributeEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String originalAttributeString = configurationSection.getString("attribute", "").toUpperCase();
        String attributeString = originalAttributeString;
        if (!(attributeString.equals("HORSE_JUMP_STRENGTH") || attributeString.equals("ZOMBIE_SPAWN_REINFORCEMENTS"))) {
            attributeString = "GENERIC_" + attributeString;
        }
        try {
            attribute = Attribute.valueOf(attributeString);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("Invalid attribute " + "\"" + originalAttributeString + "\"" + " at " + configurationSection.getCurrentPath());
        }
    }


    @Override
    public boolean useEffect(LivingEntity entity) {
        applyAttributes(entity);
        return false;
    }

    public void applyAttributes(LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        attributeInstance.setBaseValue(changeValue(attributeInstance.getBaseValue()));
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        if (entity instanceof LivingEntity) {
            applyAttributes((LivingEntity) entity);
        }
        return false;
    }
}
