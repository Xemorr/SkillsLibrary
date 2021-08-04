package me.xemor.skillslibrary2.conditions;

import org.bukkit.configuration.ConfigurationSection;

public abstract class ComparisonCondition extends Condition {

    private final Comparison comparison;
    private double comparedValue;

    public ComparisonCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        comparison = Comparison.fromString(configurationSection.getString("comparison", "EQUAL"));
        comparedValue = configurationSection.getDouble("value");
    }

    public boolean checkComparison(double value) {
        switch (comparison) {
            case EQUAL: return value == comparedValue;
            case LESS: return value < comparedValue;
            case GREATER: return value > comparedValue;
            case LESSEQUAL: return value <= comparedValue;
            case GREATEREQUAL: return value >= comparedValue;
        }
        return true;
    }

    public enum Comparison {
        EQUAL, LESS, GREATER, GREATEREQUAL, LESSEQUAL;

        public static Comparison fromString(String string) {
            string = string.toUpperCase();
            switch (string) {
                case "=": //drop down
                case "==": //drop down
                case "EQUAL":
                    return EQUAL;
                case "<": //drop down
                case "LESS":
                    return LESS;
                case ">": //drop down
                case "GREATER":
                    return GREATER;
                case "<=": //drop down
                case "LESSEQUAL":
                    return LESSEQUAL;
                case ">=": //drop down
                case "GREATEREQUAL":
                    return GREATEREQUAL;
            }
            return EQUAL;
        }
    }


}
