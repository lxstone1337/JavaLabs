package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPop {

    @Test
    void testPopRemovesTop() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(10.0);
        ctx.push(20.0);
        Pop cmd = new Pop();
        cmd.execute(new String[]{}, ctx);
        assertEquals(10.0, ctx.peek());
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testPopOnEmptyStackThrows() {
        Pop cmd = new Pop();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testPopWithArgumentsThrows() {
        Pop cmd = new Pop();
        CalcContext ctx = new CalcContext();
        ctx.push(1.0);
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"extra"}, ctx));
    }
}