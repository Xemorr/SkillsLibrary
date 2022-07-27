package me.xemor.skillslibrary2.conditions;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ComparisonCondition extends Condition {

    private Comparison comparison;
    private final double comparedValue;

    public ComparisonCondition(int condition, ConfigurationSection configurationSection) {
        super(condition, configurationSection);
        String comparisonStr = configurationSection.getString("comparison", "EQUAL");
        try {
            comparison = Comparison.fromString(comparisonStr);
        } catch (IllegalArgumentException e) {
            SkillsLibrary.getInstance().getLogger().severe("This is not a valid comparison type! " + comparisonStr + " This was found at " + configurationSection.getCurrentPath() + ".comparison");
            e.printStackTrace();
        }
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
            throw new IllegalArgumentException("This is not a valid comparison type!");
        }
    }


}
