package me.xemor.skillslibrary.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class Effect {

    private final int effect;
    private final ConfigurationSection configurationSection;

    public Effect(int effect, ConfigurationSection configurationSection) {
        this.effect = effect;
        this.configurationSection = configurationSection;
    }

    public ConfigurationSection getData() {
        return configurationSection;
    }

    public int getEffect() {
        return effect;
    }

    @Nullable
    public static Effect create(int effect, ConfigurationSection configurationSection) {
        try {
            return Effects.getClass(effect).getConstructor(int.class, ConfigurationSection.class).newInstance(effect, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum Mode {
        SELF, OTHER, BLOCK, ALL;

        public boolean runs(Mode target) {
            return this == Mode.ALL || this == target;
        }
    }

}
