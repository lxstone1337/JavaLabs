package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestSqrt {

    @Test
    void testSqrtPositive() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(25);
        Sqrt cmd = new Sqrt();
        cmd.execute(new String[]{}, ctx);
        assertEquals(5.0, ctx.peek());
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testSqrtZero() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(0);
        Sqrt cmd = new Sqrt();
        cmd.execute(new String[]{}, ctx);
        assertEquals(0.0, ctx.peek());
    }

    @Test
    void testSqrtNegativeThrows() {
        CalcContext ctx = new CalcContext();
        ctx.push(-4);
        Sqrt cmd = new Sqrt();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testSqrtEmptyStackThrows() {
        Sqrt cmd = new Sqrt();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testSqrtWithArgumentsThrows() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(9);
        Sqrt cmd = new Sqrt();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"extra"}, ctx));
    }
}