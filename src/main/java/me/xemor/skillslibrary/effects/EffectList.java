package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.Mode;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EffectList implements Iterable<Effect> {

    List<Effect> effects = new ArrayList<>();

    public EffectList(ConfigurationSection effectsSection) {
        loadEffects(effectsSection);
    }

    private void loadEffect(ConfigurationSection effectSection) {
        String effectTypeStr = effectSection.getString("type", "FLING");
        int effectType = Effects.getEffect(effectTypeStr);
        if (effectType == -1) {
            Bukkit.getLogger().warning("Invalid Effect Specified: " + effectTypeStr);
        }
        effects.add(Effect.create(effectType, effectSection));
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

    public boolean handleEffects(LivingEntity entity, Object... objects) {
        boolean result = false;
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Effect effect : effects) {
            if (effect instanceof EntityEffect && effect.getMode().runs(Mode.SELF)) {
                EntityEffect entityEffect = (EntityEffect) effect;
                result = entityEffect.useEffect(entity);
            }
            if (effect instanceof TargetEffect && effect.getMode().runs(Mode.OTHER) && otherObject instanceof Entity) {
                TargetEffect targetEffect = (TargetEffect) effect;
                result |= targetEffect.useEffect(entity, (Entity) otherObject);
            }
            else if (effect instanceof BlockEffect && effect.getMode().runs(Mode.BLOCK) && otherObject instanceof Block) {
                BlockEffect blockEffect = (BlockEffect) effect;
                result |= blockEffect.useEffect(entity, (Block) otherObject);
            }
        }
        return result;
    }

    @NotNull
    @Override
    public Iterator<Effect> iterator() {
        return effects.iterator();
    }
}
