package calculator;

import calculator.commands.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    private final CommandFactory factory = new CommandFactory();

    @Test
    void testCreatePushCommand() throws CalcException {
        Command cmd = factory.createCommand("PUSH");
        assertInstanceOf(Push.class, cmd);
    }

    @Test
    void testCreatePopCommand() throws CalcException {
        Command cmd = factory.createCommand("POP");
        assertInstanceOf(Pop.class, cmd);
    }

    @Test
    void testCreatePrintCommand() throws CalcException {
        Command cmd = factory.createCommand("PRINT");
        assertInstanceOf(Print.class, cmd);
    }

    @Test
    void testCreateDefineCommand() throws CalcException {
        Command cmd = factory.createCommand("DEFINE");
        assertInstanceOf(Define.class, cmd);
    }

    @Test
    void testCreateSqrtCommand() throws CalcException {
        Command cmd = factory.createCommand("SQRT");
        assertInstanceOf(Sqrt.class, cmd);
    }

    @Test
    void testCreateAddCommand() throws CalcException {
        Command cmd = factory.createCommand("+");
        assertInstanceOf(Add.class, cmd);
        cmd = factory.createCommand("ADD");
        assertInstanceOf(Add.class, cmd);
    }

    @Test
    void testCreateSubtractCommand() throws CalcException {
        Command cmd = factory.createCommand("-");
        assertInstanceOf(Subtract.class, cmd);
        cmd = factory.createCommand("SUB");
        assertInstanceOf(Subtract.class, cmd);
    }

    @Test
    void testCreateMultiplyCommand() throws CalcException {
        Command cmd = factory.createCommand("*");
        assertInstanceOf(Multiply.class, cmd);
        cmd = factory.createCommand("MUL");
        assertInstanceOf(Multiply.class, cmd);
    }

    @Test
    void testCreateDivideCommand() throws CalcException {
        Command cmd = factory.createCommand("/");
        assertInstanceOf(Divide.class, cmd);
        cmd = factory.createCommand("DIV");
        assertInstanceOf(Divide.class, cmd);
    }

    @Test
    void testUnknownCommandThrows() {
        assertThrows(CalcException.class, () -> factory.createCommand("UNKNOWN"));
    }
}