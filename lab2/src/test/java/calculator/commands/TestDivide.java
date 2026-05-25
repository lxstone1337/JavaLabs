package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestDivide {

    @Test
    void testDivide() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(20);
        ctx.push(4);
        Divide cmd = new Divide();
        cmd.execute(new String[]{}, ctx);
        assertEquals(5.0, ctx.peek());
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testDivideByZeroThrows() {
        CalcContext ctx = new CalcContext();
        ctx.push(10);
        ctx.push(0);
        Divide cmd = new Divide();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testDivideWithNegative() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(-10);
        ctx.push(2);
        Divide cmd = new Divide();
        cmd.execute(new String[]{}, ctx);
        assertEquals(-5.0, ctx.peek());
    }

    @Test
    void testDivideWithDecimal() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        ctx.push(2);
        Divide cmd = new Divide();
        cmd.execute(new String[]{}, ctx);
        assertEquals(2.5, ctx.peek());
    }

    @Test
    void testDivideTooFewElements() {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        Divide cmd = new Divide();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }
}