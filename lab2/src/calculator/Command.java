package calculator;

public interface Command {
    void execute(String[] args, CalcContext context) throws CalcException;
}