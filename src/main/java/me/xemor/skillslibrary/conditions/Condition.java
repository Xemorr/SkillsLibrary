package me.xemor.skillslibrary.conditions;

import me.xemor.configurationdata.ConfigurationData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class Condition {

    private final int condition;
    private final ConfigurationSection configurationSection;
    private ConditionMode mode;

    public Condition(int condition, ConfigurationSection configurationSection) {
        this.condition = condition;
        this.configurationSection = configurationSection;
        String targetStr = configurationSection.getString("mode", "ALL").toUpperCase();
        try {
            mode = ConditionMode.valueOf(targetStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("Invalid target specified at " + configurationSection.getCurrentPath() + ".mode");
        }

    }

    @Nullable
    public static Condition create(int condition, ConfigurationSection configurationSection) {
        try {
            return Conditions.getClass(condition).getConstructor(int.class, ConfigurationSection.class).newInstance(condition, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ConfigurationSection getData() {
        return configurationSection;
    }

    public int getCondition() {
        return condition;
    }

    public ConditionMode getMode() {
        return mode;
    }

    public enum ConditionMode {
        SELF, OTHER, BLOCK, ALL;

        public boolean runs(ConditionMode target) {
            return this == ConditionMode.ALL || this == target;
        }
    }
}
