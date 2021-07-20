package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.Mode;
import me.xemor.skillslibrary.conditions.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
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
            boolean result = true;
            if (condition instanceof EntityCondition && condition.getMode().runs(Mode.SELF)) {
                EntityCondition entityCondition = (EntityCondition) condition;
                result = entityCondition.isTrue(skillEntity);
            }
            if (condition instanceof TargetCondition && objects[0] instanceof Entity && condition.getMode().runs(Mode.OTHER)) {
                TargetCondition targetCondition = (TargetCondition) condition;
                result = targetCondition.isTrue(skillEntity, (Entity) objects[0]);
            }
            if (condition instanceof BlockCondition && objects[0] instanceof Block && condition.getMode().runs(Mode.BLOCK)) {
                BlockCondition blockCondition = (BlockCondition) condition;
                result = blockCondition.isTrue(skillEntity, (Block) objects[0]);
            }
            if (!result) return false;
        }
        return true;
    }

    protected boolean executeEffects(LivingEntity entity, Object... objects) {
        for (Effect effect : nextEffects) {
            if (effect.getMode().runs(Mode.SELF)) {
                EntityEffect entityEffect = (EntityEffect) effect;
                return entityEffect.useEffect(entity);
            }
            else if (effect.getMode().runs(Mode.OTHER) && objects[0] instanceof Entity) {
                TargetEffect targetEffect = (TargetEffect) effect;
                return targetEffect.useEffect(entity, (Entity) objects[0]);
            }
            else if (effect.getMode().runs(Mode.BLOCK) && objects[0] instanceof Block) {
                BlockEffect blockEffect = (BlockEffect) effect;
                return blockEffect.useEffect(entity, (Block) objects[0]);
            }
        }
        return false;
    }

}
