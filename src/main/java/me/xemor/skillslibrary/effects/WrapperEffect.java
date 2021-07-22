package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.conditions.ConditionList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

public abstract class WrapperEffect extends Effect {

    private final ConditionList conditions;
    private final EffectList effects;

    public WrapperEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        ConfigurationSection conditionSection = configurationSection.getConfigurationSection("conditions");
        conditions = new ConditionList(conditionSection);
        ConfigurationSection effectsSection = configurationSection.getConfigurationSection("effects");
        effects = new EffectList(effectsSection);

    }

    public boolean handleEffects(LivingEntity entity, Object... objects) {
        if (conditions.areConditionsTrue(entity, objects)) {
            return effects.handleExactEffects(entity, objects);
        }
        return false;
    }

    public ConditionList getConditions() {
        return conditions;
    }

    public EffectList getEffects() {
        return effects;
    }
}
