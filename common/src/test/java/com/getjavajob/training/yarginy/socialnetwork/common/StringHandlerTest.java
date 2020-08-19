package com.getjavajob.training.yarginy.socialnetwork.common;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataChecker;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataChecker.stringTrim;
import static com.getjavajob.training.yarginy.socialnetwork.common.utils.TestResultPrinter.printPassed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringHandlerTest {
    private static final String CLASS = "StringHandlerTest";
    private static String givenString;
    private boolean caughtException;

    @Test
    public void testPrepareString() {
        givenString = " aaAAaa   ";
        assertEquals("aaaaaa", stringTrim(givenString));
        printPassed(CLASS, "testPrepareString");
    }

    @Test
    public void testCheckString() {
        givenString = "";
        assertException(DataChecker::stringPrepare);
        givenString = null;
        assertException(DataChecker::stringPrepare);
        printPassed(CLASS, "testCheckString");
    }

    @Test
    public void testCheckEmail() {
        givenString = ".asd@asd.";
        assertException(DataChecker::emailCheck);
        givenString = "a.sd@a.sd";
        assertException(DataChecker::emailCheck);
        givenString = "a..sd@asd.asd";
        assertException(DataChecker::emailCheck);
        givenString = "a%$@asd.asd";
        assertException(DataChecker::emailCheck);
        givenString = "asd.@.asd.asd";
        assertException(DataChecker::emailCheck);
        givenString = "a.s.d@asd.as";
        assertException(DataChecker::emailCheck);
        givenString = "asd@as.d.as";
        assertException(DataChecker::emailCheck);
        givenString = "asd@asd.asd";
        printPassed(CLASS, "testCheckEmail");
    }

    private void assertException(StringHandlerMethod method) {
        try {
            method.callMethod(givenString);
        } catch (IncorrectDataException e) {
            caughtException = true;
        }
        assertTrue(caughtException);
        caughtException = false;
    }

    private interface StringHandlerMethod {
        void callMethod(String string);
    }
}
