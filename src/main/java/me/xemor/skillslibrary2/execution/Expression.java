package me.xemor.skillslibrary2.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.xemor.configurationdata.deserializers.text.TextDeserializer;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataHolder;
import org.gradle.internal.impldep.com.fasterxml.jackson.annotation.JsonCreator;

import java.util.*;
import java.util.function.Function;

public class Expression {

    private Double cachedResult = null;
    private List<String> postfix = Collections.emptyList();
    List<Function<Double, Double>> postprocessors = new ArrayList<>(1);

    @JsonCreator
    public Expression(String message) {
        try {
            cachedResult = new Execution().evaluatePostfix(Execution.infixToPostfix(message), Map.of());
        } catch (IllegalArgumentException e) {
            this.postfix = Execution.infixToPostfix(message);
        }
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

    public Expression(double result) {
        this.cachedResult = result;
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

    public class ExpressionDeserializer extends TextDeserializer<Expression> {
        @Override
        public Expression deserialize(String text) {
            return new Expression(text);
        }
    }

}
