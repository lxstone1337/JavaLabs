package calculator.commands;

import calculator.CalcContext;
import calculator.CalcException;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class TestPrint {

    @Test
    void testPrintDoesNotModifyStack() throws CalcException {
        CalcContext ctx = new CalcContext();
        ctx.push(123.45);
        Print cmd = new Print();

        // Перенаправляем System.out для проверки
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            cmd.execute(new String[]{}, ctx);
            assertEquals(123.45, ctx.peek()); // стек не изменился
            assertEquals(1, ctx.getStackSize());
            assertTrue(outContent.toString().contains("123.45"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintOnEmptyStackThrows() {
        Print cmd = new Print();
        CalcContext ctx = new CalcContext();
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{}, ctx));
    }

    @Test
    void testPrintWithArgumentsThrows() throws CalcException {
        Print cmd = new Print();
        CalcContext ctx = new CalcContext();
        ctx.push(1.0);
        assertThrows(CalcException.class, () -> cmd.execute(new String[]{"extra"}, ctx));
    }
}