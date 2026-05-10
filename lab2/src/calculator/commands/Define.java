package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Define implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 2) {
            throw new CalcException("DEFINE requires two arguments: name and value");
        }
        String name = args[0];
        double value;
        try {
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new CalcException("Invalid number format for DEFINE: " + args[1]);
        }
        context.defineParameter(name, value);
    }
}
