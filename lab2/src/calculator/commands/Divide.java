package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Divide implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 0) {
            throw new CalcException("DIVIDE command takes no arguments");
        }
        double b = context.pop();
        double a = context.pop();
        if (b == 0.0) {
            throw new CalcException("Division by zero");
        }
        context.push(a / b);
    }
}