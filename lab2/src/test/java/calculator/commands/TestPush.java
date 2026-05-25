package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPush {

    @Test
    void testPushNumber() throws CalcException {
        CalcContext ctx = new CalcContext();
        Push cmd = new Push();
        cmd.execute(new String[]{"5.5"}, ctx);
        assertEquals(5.5, ctx.peek());
    }

    @Test
    void testPushParameter() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.defineParameter("pi", 3.14);
        Push cmd = new Push();
        cmd.execute(new String[]{"pi"}, ctx);
        assertEquals(3.14, ctx.peek());
    }

    @Test
    void testPushMissingArgument() {
        Push cmd = new Push();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testPushUndefinedParameter() {
        Push cmd = new Push();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"undefined"}, ctx));
    }
}