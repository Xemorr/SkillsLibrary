package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.conditions.Condition;
import me.xemor.skillslibrary.conditions.Conditions;
import me.xemor.skillslibrary.conditions.EntityCondition;
import me.xemor.skillslibrary.conditions.TargetCondition;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class WrapperEffect extends Effect {

    private List<Condition> conditions;
    private final List<Effect> nextEffects = new ArrayList<>();

    public WrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection effectSection = configurationSection.getConfigurationSection("effect");
        if (effectSection != null) {
            loadEffect(effectSection);
        }
        ConfigurationSection effectsSection = configurationSection.getConfigurationSection("effects");
        if (effectsSection != null) {
            loadEffects(effectsSection);
        }
        ConfigurationSection conditionsSection = configurationSection.getConfigurationSection("conditions");
        loadConditions(conditionsSection);
    }

    private void loadConditions(ConfigurationSection conditionsSection) {
        conditions = new ArrayList<>();
        if (conditionsSection == null) return;
        Map<String, Object> values = conditionsSection.getValues(false);
        for (Object item : values.values()) {
            if (item instanceof ConfigurationSection) {
                ConfigurationSection conditionSection = (ConfigurationSection) item;
                int condition = Conditions.getCondition(conditionSection.getString("type"));
                if (condition == -1) {
                    Bukkit.getLogger().warning("Invalid Condition Type");
                }
                Condition conditionData = Condition.create(condition, conditionSection);
                if (conditionData != null) {
                    conditions.add(conditionData);
                }
            }
        }
    }

    private void loadEffect(ConfigurationSection effectSection) {
        String effectTypeStr = effectSection.getString("type", "FLING");
        int effectType = Effects.getEffect(effectTypeStr);
        if (effectType == -1) {
            Bukkit.getLogger().warning("Invalid Effect Specified: " + effectTypeStr);
        }
        nextEffects.add(Effect.create(effectType, effectSection));
    }

    private void loadEffects(ConfigurationSection effectsSection) {
        Collection<Object> values = effectsSection.getValues(false).values();
        for (Object item : values) {
            if (item instanceof ConfigurationSection) {
                ConfigurationSection effectSection = (ConfigurationSection) item;
                loadEffect(effectSection);
            }
        }
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Effect> getNextEffects() {
        return nextEffects;
    }

    protected boolean areConditionsTrue(LivingEntity skillEntity, Object... objects) {
        for (Condition condition : conditions) {
            if (condition instanceof EntityCondition) {
                EntityCondition entityCondition = (EntityCondition) condition;
                boolean result = entityCondition.isTrue(skillEntity);
                if (!result) return false;
            }
            if (condition instanceof TargetCondition && objects[0] instanceof Entity) {
                TargetCondition targetCondition = (TargetCondition) condition;
                boolean result = targetCondition.isTrue(skillEntity, (Entity) objects[0]);
                if (!result) return false;
            }
        }
        return true;
    }

}
