package me.xemor.skillslibrary.effects;

import me.xemor.configurationdata.ConfigurationData;
import me.xemor.skillslibrary.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ModifyEffect extends Effect {

    private Operation operation;
    private double value;

    public ModifyEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String operationStr = configurationSection.getString("operation", "").toUpperCase();
        try {
            this.operation = Operation.valueOf(operationStr);
        } catch(IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe(configurationSection.getCurrentPath() + " has an invalid operation specified!");
            return;
        }
        this.value = configurationSection.getDouble("value", 1);
    }

    public double changeValue(double oldValue) {
        switch (operation) {
            case ADD: return oldValue + value;
            case SUBTRACT: return oldValue - value;
            case MULTIPLY: return oldValue * value;
            case DIVIDE: return oldValue / value;
            case SET: return value;
        };
        return oldValue;
    }

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, SET;
    }

    public Operation getOperation() {
        return operation;
    }
}
