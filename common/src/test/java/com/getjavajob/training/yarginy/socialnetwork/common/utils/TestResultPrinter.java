package com.getjavajob.training.yarginy.socialnetwork.common.utils;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public class TestResultPrinter {
    public static void printPassed(String className, String testName) {
        out.println(className + '.' + testName + "() passed" + lineSeparator());
    }
}
