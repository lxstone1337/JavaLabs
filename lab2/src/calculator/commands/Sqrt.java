package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Sqrt implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 0) {
            throw new CalcException("SQRT command takes no arguments");
        }
        double a = context.pop();
        if (a < 0) {
            throw new CalcException("cannot take sqrt of negative number: " + a);
        }
        context.push(Math.sqrt(a));
    }
}