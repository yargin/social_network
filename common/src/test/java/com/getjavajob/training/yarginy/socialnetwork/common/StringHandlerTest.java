package com.getjavajob.training.yarginy.socialnetwork.common;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.StringHandler;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.StringHandler.trimString;
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
        assertEquals("aaaaaa", trimString(givenString));
        printPassed(CLASS, "testPrepareString");
    }

    @Test
    public void testCheckString() {
        givenString = "";
        assertException(StringHandler::checkString);
        givenString = null;
        assertException(StringHandler::checkString);
        printPassed(CLASS, "testCheckString");
    }

    @Test
    public void testCheckEmail() {
        givenString = ".asd@asd.";
        assertException(StringHandler::checkEmail);
        givenString = "a.sd@a.sd";
        assertException(StringHandler::checkEmail);
        givenString = "a..sd@asd.asd";
        assertException(StringHandler::checkEmail);
        givenString = "a%$@asd.asd";
        assertException(StringHandler::checkEmail);
        givenString = "asd.@.asd.asd";
        assertException(StringHandler::checkEmail);
        givenString = "a.s.d@asd.as";
        assertException(StringHandler::checkEmail);
        givenString = "asd@as.d.as";
        assertException(StringHandler::checkEmail);
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
