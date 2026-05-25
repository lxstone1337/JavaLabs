package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMultiply {

    @Test
    void testMultiply() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        ctx.push(4);
        Multiply cmd = new Multiply();
        cmd.execute(new String[]{}, ctx);
        assertEquals(20.0, ctx.peek());
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testMultiplyWithZero() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(10);
        ctx.push(0);
        Multiply cmd = new Multiply();
        cmd.execute(new String[]{}, ctx);
        assertEquals(0.0, ctx.peek());
    }

    @Test
    void testMultiplyWithNegative() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(-3);
        ctx.push(4);
        Multiply cmd = new Multiply();
        cmd.execute(new String[]{}, ctx);
        assertEquals(-12.0, ctx.peek());
    }

    @Test
    void testMultiplyTooFewElements() {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        Multiply cmd = new Multiply();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }
}