package calculator;

import java.util.*;

public class CalcContext {
    private final Deque<Double> stack = new ArrayDeque<>();
    private final Map<String, Double> parameters = new HashMap<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() throws CalcException {
        if (stack.isEmpty()) {
            throw new CalcException("Stack underflow");
        }
        return stack.pop();
    }

    public double peek() throws CalcException {
        if (stack.isEmpty()) {
            throw new CalcException("Stack is empty");
        }
        return stack.peek();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public int getStackSize() {
        return stack.size();
    }

    public void defineParameter(String name, double value) {
        parameters.put(name, value);
    }

    public boolean isParameterDefined(String name) {
        return parameters.containsKey(name);
    }

    public double getParameterValue(String name) throws CalcException {
        if (!parameters.containsKey(name)) {
            throw new CalcException("Undefined parameter: " + name);
        }
        return parameters.get(name);
    }

    /*// For testing purposes
    public Deque<Double> getStackCopy() {
        return new ArrayDeque<>(stack);
    }

    public Map<String, Double> getParametersCopy() {
        return new HashMap<>(parameters);
    }*/
}