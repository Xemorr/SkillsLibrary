package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MetadataEffect extends ModifyEffect implements EntityEffect, TargetEffect {

    private NamespacedKey variable;

    @JsonCreator
    public MetadataEffect(@JsonProperty("variable") String variableStr) {
        if (variableStr == null) {
            SkillsLibrary.getInstance().getLogger().severe("You need to specify a variable name! .variable is null!");
        }
        else {
            variable = new NamespacedKey(SkillsLibrary.getInstance(), variableStr);
        }
    }

    @Override
    public void useEffect(Execution execution, Entity entity) {
        setVariable(execution, entity.getPersistentDataContainer());
    }

    @Override
    public void useEffect(Execution execution, Entity entity, Entity target) {
        SkillsLibrary.getFoliaHacks().runASAP(target, () -> setVariable(execution, target.getPersistentDataContainer()));
    }

    public void setVariable(Execution execution, PersistentDataContainer container) {
        Double value;
        value = container.get(variable, PersistentDataType.DOUBLE);
        if (value == null) {
            value = 0D;
        }
        container.set(variable, PersistentDataType.DOUBLE, changeValue(execution, value));
    }
}

