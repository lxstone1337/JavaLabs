package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestSubtract {

    @Test
    void testSubtract() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(20);
        ctx.push(10);
        Subtract cmd = new Subtract();
        cmd.execute(new String[]{}, ctx);
        assertEquals(10.0, ctx.peek()); // 20 - 10 = 10
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testSubtractNegativeResult() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        ctx.push(10);
        Subtract cmd = new Subtract();
        cmd.execute(new String[]{}, ctx);
        assertEquals(-5.0, ctx.peek()); // 5 - 10 = -5
    }

    @Test
    void testSubtractTooFewElements() {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        Subtract cmd = new Subtract();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testSubtractEmptyStack() {
        Subtract cmd = new Subtract();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }
}