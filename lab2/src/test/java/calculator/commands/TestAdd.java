package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestAdd {

    @Test
    void testAdd() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(10);
        ctx.push(20);
        Add cmd = new Add();
        cmd.execute(new String[]{}, ctx);
        assertEquals(30.0, ctx.peek());
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testAddWithNegative() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(-5);
        ctx.push(3);
        Add cmd = new Add();
        cmd.execute(new String[]{}, ctx);
        assertEquals(-2.0, ctx.peek());
    }

    @Test
    void testAddWithDecimal() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(2.5);
        ctx.push(1.5);
        Add cmd = new Add();
        cmd.execute(new String[]{}, ctx);
        assertEquals(4.0, ctx.peek());
    }

    @Test
    void testAddTooFewElements() {
        CalcContext ctx = new CalcContext();
        ctx.push(5);
        Add cmd = new Add();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testAddEmptyStack() {
        CalcContext ctx = new CalcContext();
        Add cmd = new Add();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testAddWithArgumentsThrows() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(1);
        ctx.push(2);
        Add cmd = new Add();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"extra"}, ctx));
    }
}