package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Push implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 1) {
            throw new CalcException("PUSH requires one argument");
        }
        String arg = args[0];
        double value;
        try {
            value = Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            value = context.getParameterValue(arg);
        }
        context.push(value);
    }
}