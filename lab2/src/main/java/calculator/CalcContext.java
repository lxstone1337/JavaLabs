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
            throw new CalcException("stack underflow");
        }
        return stack.pop();
    }

    public double peek() throws CalcException {
        if (stack.isEmpty()) {
            throw new CalcException("stack is empty");
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
            throw new CalcException("undefined parameter: " + name);
        }
        return parameters.get(name);
    }
}