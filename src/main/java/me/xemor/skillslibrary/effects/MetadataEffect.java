package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MetadataEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    NamespacedKey variable;

    public MetadataEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String variableStr = configurationSection.getString("variable");
        if (variableStr == null) {
            SkillsLibrary.getInstance().getLogger().severe("You need to specify a variable name! " + configurationSection.getCurrentPath() + ".variable is null!");
        }
        else {
            variable = new NamespacedKey(SkillsLibrary.getInstance(), variableStr);
        }
    }

    @Override
    public boolean useEffect(LivingEntity entity) {
        setVariable(entity.getPersistentDataContainer());
        return false;
    }

    @Override
    public boolean useEffect(LivingEntity livingEntity, Entity entity) {
        setVariable(entity.getPersistentDataContainer());
        return false;
    }


    public void setVariable(PersistentDataContainer container) {
        double value;
        if (container.has(variable, PersistentDataType.DOUBLE)) {
            value = container.get(variable, PersistentDataType.DOUBLE);
        }
        else {
            value = 0;
        }
        container.set(variable, PersistentDataType.DOUBLE, changeValue(value));
    }

}

