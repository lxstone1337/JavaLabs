package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Add implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 0) {
            throw new CalcException("ADD command takes no arguments");
        }
        double b = context.pop();
        double a = context.pop();
        context.push(a + b);
    }
}