package me.xemor.skillslibrary2.execution;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataHolder;
import org.gradle.internal.impldep.com.fasterxml.jackson.annotation.JsonCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@JsonDeserialize(using = Expression.ExpressionDeserializer.class)
public class Expression {

    private Double cachedResult = null;
    private List<String> postfix = Collections.emptyList();
    List<Function<Double, Double>> postprocessors = new ArrayList<>(1);

    public Expression(String message) {
        try {
            cachedResult = new Execution().evaluatePostfix(Execution.infixToPostfix(message), Map.of());
        } catch (IllegalArgumentException e) {
            this.postfix = Execution.infixToPostfix(message);
        }
    }

    public Expression(double result) {
        this.cachedResult = result;
    }

    public Expression apply(Function<Double, Double> postProcess) {
        postprocessors.add(postProcess);
        if (cachedResult != null) cachedResult = postProcess.apply(cachedResult);
        return this;
    }

    private double executePostProcessors(double executionCalculation) {
        for (var postprocessor : postprocessors) {
            executionCalculation = postprocessor.apply(executionCalculation);
        }
        return executionCalculation;
    }

    public double result(Execution execution) {
        return result(execution, Map.of());
    }

    public double result(Execution execution, Entity self) {
        return result(execution, Map.of("self", self));
    }

    public double result(Execution execution, Entity self, Entity target) {
        return result(execution, Map.of("self", self, "other", target));
    }

    public double result(Execution execution, Map<String, PersistentDataHolder> modeToHolder) {
        if (cachedResult != null) return cachedResult;
        else return executePostProcessors(execution.evaluatePostfix(postfix, modeToHolder));
    }

    public static class ExpressionDeserializer extends JsonDeserializer<Expression> {
        @Override
        public Expression deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonToken token = p.getCurrentToken();
            if (token.isNumeric()) return new Expression(p.getDoubleValue());
            else return new Expression(p.getText());
        }
    }

}
