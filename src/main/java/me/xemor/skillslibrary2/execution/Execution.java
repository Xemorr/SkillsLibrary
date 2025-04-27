package me.xemor.skillslibrary2.execution;

import me.xemor.skillslibrary2.SkillsLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Execution {

    private final ConcurrentHashMap<String, Double> variables = new ConcurrentHashMap<>();
    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    public Execution() {}

    public void setCancelled(boolean cancel) {
        cancelled.set(cancel);
    }

    public void setValue(String variable, double value) {
        variables.put(variable, value);
    }

    protected static List<String> infixToPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/^ ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;
            if (isNumeric(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                output.add(token); // variable case
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    protected double evaluatePostfix(List<String> postfix, Map<String, PersistentDataHolder> modeToHolders) throws IllegalArgumentException {
        var containers = modeToHolders
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                (entry) -> entry.getValue().getPersistentDataContainer())
                );
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (isNumeric(token)) {
                stack.push(Double.valueOf(token));
            } else if (isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                }
            } else {
                stack.push(getValue(token, containers));
            }
        }

        return stack.pop();
    }

    private double getValue(String variable, Map<String, PersistentDataContainer> containers) throws IllegalArgumentException {
        Double newValue = null;
        for (Map.Entry<String, PersistentDataContainer> entry : containers.entrySet()) {
            String prefix = entry.getKey().toLowerCase() + "_";
            if (variable.toLowerCase().startsWith(prefix)) {
                variable = variable.replaceFirst(prefix, "");
                newValue = entry.getValue().get(new NamespacedKey(SkillsLibrary.getInstance(), variable), PersistentDataType.DOUBLE);
            }
        }
        Double localValue = variables.get(variable.toLowerCase());
        if (localValue != null) {
            newValue = localValue;
        }
        if (newValue == null) {
            throw new IllegalArgumentException(variable + " is undefined!");
        }
        return newValue;
    }

    private static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return "+-*/^".contains(token);
    }

    private static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }

    public boolean isCancelled() {
        return cancelled.get();
    }
}
