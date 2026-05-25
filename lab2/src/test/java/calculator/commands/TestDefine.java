package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestDefine {

    @Test
    void testDefineValidNumber() throws CalcException {
        CalcContext ctx = new CalcContext();
        Define cmd = new Define();
        cmd.execute(new String[]{"a", "100"}, ctx);
        assertTrue(ctx.isParameterDefined("a"));
        assertEquals(100.0, ctx.getParameterValue("a"));
    }

    @Test
    void testDefineNegativeNumber() throws CalcException {
        CalcContext ctx = new CalcContext();
        Define cmd = new Define();
        cmd.execute(new String[]{"b", "-5.5"}, ctx);
        assertEquals(-5.5, ctx.getParameterValue("b"));
    }

    @Test
    void testDefineInvalidNumberThrows() {
        CalcContext ctx = new CalcContext();
        Define cmd = new Define();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"a", "notANumber"}, ctx));
    }

    @Test
    void testDefineWrongArgumentCount() {
        Define cmd = new Define();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"onlyOne"}, ctx));
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"a", "b", "c"}, ctx));
    }

    @Test
    void testDefineOverwritesExisting() throws CalcException {
        CalcContext ctx = new CalcContext();
        Define cmd = new Define();
        cmd.execute(new String[]{"x", "10"}, ctx);
        assertEquals(10.0, ctx.getParameterValue("x"));
        cmd.execute(new String[]{"x", "99"}, ctx);
        assertEquals(99.0, ctx.getParameterValue("x"));
    }
}