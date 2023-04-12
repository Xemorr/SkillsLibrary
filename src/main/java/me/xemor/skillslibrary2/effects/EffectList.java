package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.Mode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EffectList implements Iterable<Effect> {

    private List<Effect> effects = new ArrayList<>(1);
    private static EffectList effectList = new EffectList();

    public EffectList(ConfigurationSection effectsSection) {
        loadEffects(effectsSection);
    }

    public EffectList() {}

    private void loadEffect(ConfigurationSection effectSection) {
        String effectTypeStr = effectSection.getString("type", "FLING");
        int effectType = Effects.getEffect(effectTypeStr);
        if (effectType == -1) {
            Bukkit.getLogger().warning("Invalid Effect Specified: " + effectTypeStr + " at " + effectSection.getCurrentPath() + ".type");
            return;
        }
        effects.add(Effect.create(effectType, effectSection));
    }

    public void loadEffects(ConfigurationSection effectsSection) {
        Collection<Object> values = effectsSection.getValues(false).values();
        effects = new ArrayList<>(values.size());
        for (Object item : values) {
            if (item instanceof ConfigurationSection effectSection) {
                loadEffect(effectSection);
            }
        }
    }

    public boolean handleEffects(Entity entity, Object... objects) {
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
            else if (effect instanceof LocationEffect && effect.getMode().runs(Mode.LOCATION) && otherObject instanceof Location) {
                LocationEffect locationEffect = (LocationEffect) effect;
                result |= locationEffect.useEffect(entity, (Location) otherObject);
            }
            else if (effect instanceof ItemStackEffect && effect.getMode().runs(Mode.ITEM) && otherObject instanceof ItemStack) {
                ItemStackEffect itemStackEffect = (ItemStackEffect) effect;
                result |= itemStackEffect.useEffect(entity, (ItemStack) otherObject);
            }
        }
        return result;
    }

    public boolean handleExactEffects(Entity entity, Object... objects) {
        boolean result = false;
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Effect effect : effects) {
            if (effect instanceof EntityEffect && effect.getMode().runs(Mode.SELF) && otherObject == null) {
                EntityEffect entityEffect = (EntityEffect) effect;
                result = entityEffect.useEffect(entity);
            }
            else if (effect instanceof TargetEffect && effect.getMode().runs(Mode.OTHER) && otherObject instanceof Entity) {
                TargetEffect targetEffect = (TargetEffect) effect;
                result |= targetEffect.useEffect(entity, (Entity) otherObject);
            }
            else if (effect instanceof LocationEffect && effect.getMode().runs(Mode.LOCATION) && otherObject instanceof Location) {
                LocationEffect locationEffect = (LocationEffect) effect;
                result |= locationEffect.useEffect(entity, (Location) otherObject);
            }
            else if (effect instanceof ItemStackEffect && effect.getMode().runs(Mode.ITEM) && otherObject instanceof ItemStack) {
                ItemStackEffect itemStackEffect = (ItemStackEffect) effect;
                result |= itemStackEffect.useEffect(entity, (ItemStack) otherObject);
            }
        }
        return result;
    }

    public static EffectList effectList() {
        return effectList;
    }

    @NotNull
    @Override
    public Iterator<Effect> iterator() {
        return effects.iterator();
    }
}
