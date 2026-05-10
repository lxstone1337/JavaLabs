package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import calculator.Command;

public class Pop implements Command {
    @Override
    public void execute(String[] args, CalcContext context) throws CalcException {
        if (args.length != 0) {
            throw new CalcException("POP command takes no arguments");
        }
        context.pop(); // just discard
    }
}