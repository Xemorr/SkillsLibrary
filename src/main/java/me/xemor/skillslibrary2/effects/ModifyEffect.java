package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ModifyEffect extends Effect {

    private Operation operation;
    protected double value;

    public ModifyEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        String operationStr = configurationSection.getString("operation", "").toUpperCase();
        try {
            this.operation = Operation.valueOf(operationStr);
        } catch(IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe(configurationSection.getCurrentPath() + ".operation has an invalid operation specified! Defaulting to SET operation!");
            this.operation = Operation.SET;
            return;
        }
        this.value = configurationSection.getDouble("value", 1);
    }

    public double changeValue(double oldValue) {
        return switch (operation) {
            case ADD -> oldValue + value;
            case SUBTRACT -> oldValue - value;
            case MULTIPLY -> oldValue * value;
            case DIVIDE -> oldValue / value;
            case SET -> value;
        };
    }

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, SET;
    }

    public Operation getOperation() {
        return operation;
    }
}
