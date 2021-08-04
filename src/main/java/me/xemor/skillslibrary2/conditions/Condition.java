package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.Mode;
import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class Condition {

    private final int condition;
    private Mode mode;

    public Condition(int condition, ConfigurationSection configurationSection) {
        this.condition = condition;
        String targetStr = configurationSection.getString("mode", "ALL").toUpperCase();
        try {
            mode = Mode.valueOf(targetStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("Invalid target specified at " + configurationSection.getCurrentPath() + ".mode");
        }
    }

    public Condition(int condition, Mode mode) {
        this.condition = condition;
        this.mode = mode;
    }

    @Nullable
    public static Condition create(int condition, ConfigurationSection configurationSection) {
        try {
            return Conditions.getClass(condition).getConstructor(int.class, ConfigurationSection.class).newInstance(condition, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Bukkit.getLogger().severe("Exception for " + Conditions.getClass(condition).getName());
            e.printStackTrace();
        }
        return null;
    }

    public int getCondition() {
        return condition;
    }

    public Mode getMode() {
        return mode;
    }

}
