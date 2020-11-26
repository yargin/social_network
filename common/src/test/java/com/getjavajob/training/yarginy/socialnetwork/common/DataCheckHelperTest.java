package com.getjavajob.training.yarginy.socialnetwork.common;

import com.getjavajob.training.yarginy.socialnetwork.common.exceptions.IncorrectDataException;
import com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper;
import org.junit.Test;

import static com.getjavajob.training.yarginy.socialnetwork.common.utils.DataCheckHelper.stringOptional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataCheckHelperTest {
    private static final String CLASS = "DataCheckHelperTest";
    private static String givenString;
    private boolean caughtException;

    @Test
    public void testStringTrim() {
        givenString = " aaAAaa   ";
        assertEquals("aaAAaa", stringOptional(givenString));
    }

    @Test
    public void testStringPrepare() {
        givenString = "";
        assertException(DataCheckHelper::stringMandatory);
        givenString = null;
        assertException(DataCheckHelper::stringMandatory);
    }

    @Test
    public void testEmailCheck() {
        givenString = ".asd@asd.";
        assertException(DataCheckHelper::emailOptional);
        givenString = "a.sd@a.sd";
        assertException(DataCheckHelper::emailOptional);
        givenString = "a..sd@asd.asd";
        assertException(DataCheckHelper::emailOptional);
        givenString = "a%$@asd.asd";
        assertException(DataCheckHelper::emailOptional);
        givenString = "asd.@.asd.asd";
        assertException(DataCheckHelper::emailOptional);
        givenString = "a.s.d@asd.as";
        assertException(DataCheckHelper::emailOptional);
        givenString = "asd@as.d.as";
        assertException(DataCheckHelper::emailOptional);
        givenString = "asd@asd.asd";
        assertNoException(DataCheckHelper::emailOptional);
    }

    @Test
    public void testPhoneCheck() {
        givenString = "+7  22222";
        assertException(DataCheckHelper::phoneOptional);
        givenString = "123124  123123";
        assertException(DataCheckHelper::phoneOptional);
        givenString = "1..sd asd.2";
        assertException(DataCheckHelper::phoneOptional);
        givenString = "+124124124";
        assertNoException(DataCheckHelper::phoneOptional);
        givenString = "+1(241)24124";
        assertNoException(DataCheckHelper::phoneOptional);
        givenString = "124(124)124";
        assertNoException(DataCheckHelper::phoneOptional);
        givenString = "+12-412-4124";
        assertNoException(DataCheckHelper::phoneOptional);
        givenString = "12 ( 412 ) 41 24";
        assertNoException(DataCheckHelper::phoneOptional);
    }

    @Test
    public void testPasswordCheck() {
        givenString = "aaa";
        assertException(DataCheckHelper::passwordMandatory);
        givenString = "111111111111111111111111111111111111111";
        assertException(DataCheckHelper::passwordMandatory);
        givenString = "aaaaaa";
        assertException(DataCheckHelper::passwordMandatory);
        givenString = "111111";
        assertException(DataCheckHelper::passwordMandatory);
        givenString = "1a1a1a";
        assertNoException(DataCheckHelper::passwordMandatory);
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

    private void assertNoException(StringHandlerMethod method) {
        method.callMethod(givenString);
        assertTrue(true);
    }

    private interface StringHandlerMethod {
        void callMethod(String string);
    }
}
