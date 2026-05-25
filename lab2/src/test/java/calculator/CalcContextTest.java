package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalcContextTest {

    @Test
    void testPushAndPop() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(10.5);
        ctx.push(20.0);
        assertEquals(20.0, ctx.pop());
        assertEquals(10.5, ctx.pop());
    }

    @Test
    void testPopOnEmptyThrows() {
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, ctx::pop);
    }

    @Test
    void testPeek() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(42.0);
        assertEquals(42.0, ctx.peek());
        assertEquals(42.0, ctx.peek()); // peek не удаляет
        assertEquals(1, ctx.getStackSize());
    }

    @Test
    void testDefineAndGetParameter() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.defineParameter("x", 3.14);
        assertTrue(ctx.isParameterDefined("x"));
        assertEquals(3.14, ctx.getParameterValue("x"));
        assertFalse(ctx.isParameterDefined("y"));
        assertThrows(CalcException.class, () -> ctx.getParameterValue("y"));
    }
}