package me.xemor.skillslibrary.effects;

import me.xemor.skillslibrary.Mode;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class Effect {

    private final int effect;
    private final ConfigurationSection configurationSection;
    private Mode mode = null;

    public Effect(int effect, ConfigurationSection configurationSection) {
        this.effect = effect;
        this.configurationSection = configurationSection;
        try {
            mode = Mode.valueOf(configurationSection.getString("mode", "ALL"));
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("You have entered an invalid mode! " + configurationSection.getCurrentPath() + ".mode");
        }
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

    public Mode getMode() {
        return mode;
    }

}
