package me.xemor.skillslibrary.conditions;

import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MetadataCondition extends ComparisonCondition implements EntityCondition, TargetCondition {

    NamespacedKey variable;

    public MetadataCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        String variableStr = configurationSection.getString("variable");
        if (variableStr == null) {
            SkillsLibrary.getInstance().getLogger().severe("You need to specify a variable name! " + configurationSection.getCurrentPath() + ".variable is null!");
        }
        else {
            variable = new NamespacedKey(SkillsLibrary.getInstance(), variableStr);
        }
    }

    @Override
    public boolean isTrue(LivingEntity boss) {
        return isTrue(boss.getPersistentDataContainer());
    }

    @Override
    public boolean isTrue(LivingEntity skillEntity, Entity target) {
        return isTrue(target.getPersistentDataContainer());
    }

    public boolean isTrue(PersistentDataContainer container) {
        Double value = container.get(variable, PersistentDataType.DOUBLE);
        return checkComparison(value == null ? 0 : value);
    }

}
