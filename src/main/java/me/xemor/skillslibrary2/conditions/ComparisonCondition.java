package me.xemor.skillslibrary2.conditions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.CompulsoryJsonProperty;
import me.xemor.configurationdata.JsonPropertyWithDefault;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;
import me.xemor.skillslibrary2.execution.Execution;
import me.xemor.skillslibrary2.execution.Expression;

public abstract class ComparisonCondition extends Condition {

    @JsonPropertyWithDefault
    private Comparison comparison = Comparison.EQUAL;
    @CompulsoryJsonProperty
    private Expression value = null;

    public boolean checkComparison(Execution execution, double inputValue) {
        double comparedValue = value.result(execution);
        return switch (comparison) {
            case EQUAL -> inputValue == comparedValue;
            case LESS -> inputValue < comparedValue;
            case GREATER -> inputValue > comparedValue;
            case LESSEQUAL -> inputValue <= comparedValue;
            case GREATEREQUAL -> inputValue >= comparedValue;
        };
    }

    @JsonDeserialize(using = Comparison.ComparisonDeserializer.class)
    public enum Comparison {
        EQUAL, LESS, GREATER, GREATEREQUAL, LESSEQUAL;

        public static class ComparisonDeserializer extends TextDeserializer<Comparison> {
            @Override
            public Comparison deserialize(String text, JsonParser jsonParser, DeserializationContext ctxt) {
                text = text.toUpperCase();
                return switch (text) {
                    case "=", "==", "EQUAL" -> EQUAL;
                    case "<", "LESS" -> LESS;
                    case ">", "GREATER" -> GREATER;
                    case "<=", "LESSEQUAL" -> LESSEQUAL;
                    case ">=", "GREATEREQUAL" -> GREATEREQUAL;
                    default -> throw new IllegalArgumentException("This is not a valid comparison type!");
                };
            }
        }
    }


}
