package me.xemor.skillslibrary2.effects;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.execution.Execution;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectList implements Iterable<Effect> {

    private static final EffectList emptyList = new EffectList();

    @JsonIgnore
    private final List<Effect> effects = new ArrayList<>(1);

    public EffectList() {}

    @JsonAnySetter
    public void loadEffect(String name, Effect effect) {
        effects.add(effect);
    }

    public boolean handleEffects(Execution execution, Entity entity, Object... objects) {
        boolean result = false;
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Effect effect : effects) {
            if (effect instanceof EntityEffect entityEffect && effect.getMode().runs(Mode.SELF)) {
                entityEffect.useEffect(execution, entity);
            }
            if (effect instanceof TargetEffect complexTargetEffect && effect.getMode().runs(Mode.OTHER) && otherObject instanceof Entity) {
                complexTargetEffect.useEffect(execution, entity, (Entity) otherObject);
            }
            else if (effect instanceof ComplexLocationEffect complexLocationEffect && effect.getMode().runs(Mode.LOCATION) && otherObject instanceof Location) {
                complexLocationEffect.useEffect(execution, entity, (Location) otherObject);
            }
            else if (effect instanceof ItemStackEffect itemStack && effect.getMode().runs(Mode.ITEM) && otherObject instanceof ItemStack) {
                itemStack.useEffect(execution, entity, (ItemStack) otherObject);
            }
        }
        return result;
    }

    public boolean handleExactEffects(Execution execution, Entity entity, Object... objects) {
        boolean result = false;
        Object otherObject = objects.length == 0 ? null : objects[0];
        for (Effect effect : effects) {
            if (effect instanceof EntityEffect entityEffect && effect.getMode().runs(Mode.SELF) && otherObject == null) {
                entityEffect.useEffect(execution, entity);
            }
            else if (effect instanceof TargetEffect complexTargetEffect && effect.getMode().runs(Mode.OTHER) && otherObject instanceof Entity) {
                complexTargetEffect.useEffect(execution, entity, (Entity) otherObject);
            }
            else if (effect instanceof ComplexLocationEffect complexLocationEffect && effect.getMode().runs(Mode.LOCATION) && otherObject instanceof Location) {
                complexLocationEffect.useEffect(execution, entity, (Location) otherObject);
            }
            else if (effect instanceof ItemStackEffect itemStackEffect && effect.getMode().runs(Mode.ITEM) && otherObject instanceof ItemStack) {
                itemStackEffect.useEffect(execution, entity, (ItemStack) otherObject);
            }
        }
        return result;
    }

    public static EffectList emptyEffectsList() {
        return emptyList;
    }

    @NotNull
    @Override
    public Iterator<Effect> iterator() {
        return effects.iterator();
    }
}
